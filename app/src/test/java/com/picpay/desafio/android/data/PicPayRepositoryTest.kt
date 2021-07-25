package com.picpay.desafio.android.data

import com.picpay.desafio.android.commons.Either
import com.picpay.desafio.android.data.local.PicPayLocalDataSource
import com.picpay.desafio.android.data.local.UserEntity
import com.picpay.desafio.android.data.local.dao.Config
import com.picpay.desafio.android.data.remote.PicPayRemoteDataSource
import com.picpay.desafio.android.data.remote.UserResponse
import com.picpay.desafio.android.data.utils.CacheStrategy
import com.picpay.desafio.android.data.utils.Wrapper
import com.picpay.desafio.android.domain.User
import io.kotlintest.shouldBe
import io.mockk.*
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PicPayRepositoryTest {
    private lateinit var databaseProvider: PicPayLocalDataSource
    private var remoteProvider: PicPayRemoteDataSource = spyk()
    private lateinit var repository: PicPayRepository
    private val wrapper = Wrapper()

    private val usersResponse = listOf(
        UserResponse(11, "", "José", "jose"),
        UserResponse(4, "", "Fernanda", "fernandar"),
        UserResponse(333, "", "Maria", "mariav")
    )
    private val usersEntity = listOf(
        UserEntity(11, "", "José", "jose"),
        UserEntity(4, "", "Fernanda", "fernandar"),
        UserEntity(333, "", "Maria", "mariav")
    )
    private val users = listOf(
        User("", "José", "jose"),
        User("", "Fernanda", "fernandar"),
        User("", "Maria", "mariav")
    )

    @Before
    fun setup() {
        databaseProvider = mockk(relaxed = true)
        repository = PicPayRepositoryImpl(wrapper, remoteProvider, databaseProvider)
        mockkObject(CacheStrategy)
    }

    @Test
    fun `GIVEN cache is expired WHEN getUsers() is called THEN remote data source is called with success`() {
        runBlocking {
            // GIVEN
            every { CacheStrategy.isCacheExpired(any()) } returns true
            coEvery { remoteProvider.getUsers() } returns usersResponse
            coEvery { databaseProvider.getUsers() } returns flowOf(usersEntity)
            coEvery { databaseProvider.getConfig(any()) } returns Config("name", 543543L)

            // WHEN
            val result = repository.getUsers(false)

            // THEN
            coVerify(exactly = 1) { remoteProvider.getUsers() }
            coVerify(exactly = 1) { databaseProvider.updateUsers(usersEntity) }
            coVerify(exactly = 1) { databaseProvider.getUsers() }
            assert(result is Either.Success)
            (result as Either.Success).success.firstOrNull() shouldBe users
        }
    }

    @Test
    fun `GIVEN cache is not expired WHEN getUsers() is called THEN remote data source is not called`() {
        runBlocking {
            // GIVEN
            every { CacheStrategy.isCacheExpired(any()) } returns false
            coEvery { databaseProvider.getUsers() } returns flowOf(usersEntity)
            coEvery { databaseProvider.getConfig(any()) } returns Config("name", 543543L)

            // WHEN
            val result = repository.getUsers(false)

            // THEN
            coVerify(exactly = 0) { remoteProvider.getUsers() }
            coVerify(exactly = 0) { databaseProvider.updateUsers(usersEntity) }
            coVerify(exactly = 1) { databaseProvider.getUsers() }
            assert(result is Either.Success)
            (result as Either.Success).success.firstOrNull() shouldBe users
        }
    }

    @Test
    fun `GIVEN forceUpdate is true WHEN getUsers() is called THEN remote data source is called with success`() {
        runBlocking {
            // GIVEN
            every { CacheStrategy.isCacheExpired(any()) } returns false
            coEvery { remoteProvider.getUsers() } returns usersResponse
            coEvery { databaseProvider.getUsers() } returns flowOf(usersEntity)
            coEvery { databaseProvider.getConfig(any()) } returns Config("name", 543543L)

            // WHEN
            val result = repository.getUsers(true)

            // THEN
            coVerify(exactly = 1) { remoteProvider.getUsers() }
            coVerify(exactly = 1) { databaseProvider.updateUsers(usersEntity) }
            coVerify(exactly = 1) { databaseProvider.getUsers() }
            assert(result is Either.Success)
            (result as Either.Success).success.firstOrNull() shouldBe users
        }
    }

    @Test
    fun `GIVEN service returns error WHEN getUsers() is called THEN local data is provided`() {
        runBlocking {
            // GIVEN
            every { CacheStrategy.isCacheExpired(any()) } returns true
            coEvery { remoteProvider.getUsers() } throws Exception()
            coEvery { databaseProvider.getUsers() } returns flowOf(usersEntity)
            coEvery { databaseProvider.getConfig(any()) } returns Config("name", 543543L)

            // WHEN
            val result = repository.getUsers(false)

            // THEN
            coVerify(exactly = 1) { remoteProvider.getUsers() }
            coVerify(exactly = 0) { databaseProvider.updateUsers(usersEntity) }
            coVerify(exactly = 1) { databaseProvider.getUsers() }
            assert(result is Either.Success)
            (result as Either.Success).success.firstOrNull() shouldBe users
        }
    }

    @Test
    fun `GIVEN database returns error WHEN getUsers() is called THEN Failure is returned`() {
        runBlocking {
            // GIVEN
            every { CacheStrategy.isCacheExpired(any()) } returns true
            coEvery { remoteProvider.getUsers() } throws Exception()
            coEvery { databaseProvider.getUsers() } throws Exception()
            coEvery { databaseProvider.getConfig(any()) } returns Config("name", 543543L)

            // WHEN
            val result = repository.getUsers(false)

            // THEN
            coVerify(exactly = 1) { remoteProvider.getUsers() }
            coVerify(exactly = 0) { databaseProvider.updateUsers(usersEntity) }
            coVerify(exactly = 1) { databaseProvider.getUsers() }
            assert(result is Either.Failure)
        }
    }
}