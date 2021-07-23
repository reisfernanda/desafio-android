package com.picpay.desafio.android.data.remote

import com.picpay.desafio.android.commons.Either
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class RequestWrapper {
    suspend fun <D> wrapper(call: suspend () -> Flow<D>): Either<Throwable, Flow<D>> {
        return try {
            val data = call()
            Either.Success(data)
        } catch (httpException: HttpException) {
            return Either.Failure(httpException)
        } catch (e: Exception) {
            Either.Failure(e)
        }
    }
}