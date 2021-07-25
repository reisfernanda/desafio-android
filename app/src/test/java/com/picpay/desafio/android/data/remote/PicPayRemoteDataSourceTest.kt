package com.picpay.desafio.android.data.remote

import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PicPayRemoteDataSourceTest {
    lateinit var service: PicPayService
    lateinit var dataSource: PicPayRemoteDataSource
    private val users = listOf(
        UserResponse(11, "", "José", "jose"),
        UserResponse(5, "", "João Silva!!", "joaos"),
        UserResponse(4, "", "Fernanda", "fernandar"),
        UserResponse(333, "", "Maria", "mariav")
    )

    @Before
    fun setup() {
        service = mockk(relaxed = true)
        dataSource =  PicPayRemoteDataSourceImpl(service)
    }

    @Test
    fun `WHEN getAll method is called THEN the results are returned`() {
        runBlocking {
            // GIVEN
            coEvery { service.getUsers() } returns users

            // WHEN
            val result = dataSource.getUsers()

            // THEN
            result shouldBe users
            coVerify(exactly = 1) { service.getUsers() }
        }
    }
}