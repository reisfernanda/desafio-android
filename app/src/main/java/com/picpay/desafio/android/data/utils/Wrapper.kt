package com.picpay.desafio.android.data.utils

import com.picpay.desafio.android.commons.Either
import kotlinx.coroutines.flow.Flow

class Wrapper {
    suspend fun <D> wrap(call: suspend () -> Flow<D>): Either<Throwable, Flow<D>> {
        return try {
            val data = call()
            Either.Success(data)
        } catch (e: Exception) {
            Either.Failure(e)
        }
    }
}