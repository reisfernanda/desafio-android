package com.picpay.desafio.android.commons

sealed class Either<out F, out S> {

    data class Failure<out F>(val failure: F): Either<F, Nothing>()

    data class Success<out S>(val success: S): Either<Nothing, S>()

    fun either(onSuccess: (S) -> Any, onFailure: (F) -> Any): Any = when (this) {
        is Failure -> onFailure(failure)
        is Success -> onSuccess(success)
    }
}