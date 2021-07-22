package com.picpay.desafio.android.data.remote

import com.picpay.desafio.android.commons.Either
import retrofit2.HttpException

class RequestWrapper {
    suspend fun <D> wrapper(call: suspend () -> D): Either<Throwable, D> {
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