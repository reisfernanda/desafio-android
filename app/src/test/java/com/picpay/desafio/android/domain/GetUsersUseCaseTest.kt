package com.picpay.desafio.android.domain

import com.picpay.desafio.android.commons.Either
import com.picpay.desafio.android.commons.TestCoroutineContextProvider
import com.picpay.desafio.android.data.PicPayRepository
import io.kotlintest.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUsersUseCaseTest {
    private val repository: PicPayRepository = mockk(relaxed = true)
    private val useCase = GetUsersUseCase(repository, TestCoroutineContextProvider())
    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    private val users = listOf(
        User("", "José", "jose"),
        User("", "Fernanda", "fernandar"),
        User("", "Maria", "mariav")
    )

    @Test
    fun `GIVEN repository returns success WHEN call is called THEN result must be Success`() {
        testCoroutineScope.launch {
            coEvery { repository.getUsers(false) } returns Either.Success(flowOf(users))
            val result = useCase.call(true)

            assert(result is Either.Success)
            (result as Either.Success).success.firstOrNull() shouldBe users
        }
    }

    @Test
    fun `GIVEN repository returns error WHEN call is called THEN result must be Failure`() {
        testCoroutineScope.launch {
            coEvery { repository.getUsers(false) } returns Either.Failure(Exception())
            val result = useCase.call(true)

            assert(result is Either.Failure)
        }
    }

    @Test
    fun `GIVEN repository throws exception WHEN run is called THEN onError() must be called`() {
        testCoroutineScope.launch {
            val onSuccess: (List<User>) -> Unit = mockk()
            val onError: (Throwable) -> Unit = mockk()
            coEvery { repository.getUsers(false) } throws Exception()
            useCase.run(
                scope = testCoroutineScope,
                params = true,
                onSuccess = onSuccess,
                onError = onError
            )

            verify(exactly = 1) { onError(any()) }
            verify(exactly = 0) { onSuccess(any()) }
        }
    }

    @Test
    fun `GIVEN repository returns result WHEN run is called THEN onSuccess() must be called`() {
        testCoroutineScope.launch {
            val onSuccess: (List<User>) -> Unit = mockk()
            val onError: (Throwable) -> Unit = mockk()

            coEvery { repository.getUsers(false) } returns Either.Success(flowOf(users))
            useCase.run(
                scope = testCoroutineScope,
                params = true,
                onSuccess = onSuccess,
                onError = onError
            )

            coVerify (exactly = 0) { onError(any()) }
            coVerify (exactly = 1) { onSuccess(users) }
        }
    }
}