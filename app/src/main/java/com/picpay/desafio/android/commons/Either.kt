package com.picpay.desafio.android.commons

import kotlinx.coroutines.flow.Flow

sealed class Either<out F, out S> {

    data class Failure<out F>(val failure: F): Either<F, Nothing>()

    data class Success<out S>(val success: Flow<S>): Either<Nothing, Flow<S>>()
}