package com.picpay.desafio.android.data.local

import com.picpay.desafio.android.data.local.dao.Config
import com.picpay.desafio.android.data.local.database.AppDatabase
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PicPayLocalDataSourceTest {
    lateinit var database: AppDatabase
    lateinit var dataSource: PicPayLocalDataSource
    private val users = listOf(
        UserEntity(11, "", "José", "jose"),
        UserEntity(5, "", "João Silva!!", "joaos"),
        UserEntity(4, "", "Fernanda", "fernandar"),
        UserEntity(333, "", "Maria", "mariav"))

    @Before
    fun setup() {
        database = mockk(relaxed = true)
        dataSource =  PicPayLocalDataSourceImpl(database)
    }

    @Test
    fun `WHEN getAll method is called THEN the results are returned`() {
        runBlocking {
            // GIVEN
            coEvery { database.userDao().getAll() } returns flow { emit(users) }

            // WHEN
            val result = dataSource.getUsers()

            // THEN
            result.first() shouldBe users
            coVerify(exactly = 1) { database.userDao().getAll() }
        }
    }

    @Test
    fun `WHEN updateUsers method is called THEN the updateAll from DAO is called`() {
        runBlocking {
            // WHEN
            dataSource.updateUsers(users)

            // THEN
            coVerify(exactly = 1) {
                database.userDao().updateAll(users)
            }
        }
    }

    @Test
    fun `WHEN getConfig method is called THEN the results are returned`() {
        runBlocking {
            // GIVEN
            val configName = "config"
            val config = Config(configName, 543543L)
            coEvery { database.configDao().get(configName) } returns config

            // WHEN
            val result = dataSource.getConfig(configName)

            // THEN
            result shouldBe config
            coVerify(exactly = 1) { database.configDao().get(configName) }
        }
    }

    @Test
    fun `WHEN updateConfig method is called THEN the insert from DAO is called`() {
        runBlocking {
            // GIVEN
            val config = Config("configName", 543543L)

            // WHEN
            dataSource.updateConfig(config)

            // THEN
            coVerify(exactly = 1) {
                database.configDao().insert(config)
            }
        }
    }
}