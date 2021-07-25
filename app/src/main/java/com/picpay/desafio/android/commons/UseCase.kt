package com.picpay.desafio.android.commons

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<out T, in Params>(
    private val contextProvider: CoroutineContextProvider,
) {
    abstract suspend fun call(params: Params? = null): Either<Throwable, Flow<T>>
    fun run(
        scope: CoroutineScope,
        params: Params? = null,
        onError: ((Throwable) -> Unit) = {},
        onSuccess: (T) -> Unit = {},
        onLoading: ((Boolean) -> Unit) = {}
    ) {
        scope.launch(contextProvider.io) {
            try {
                onLoading(true)
                val either = call(params)
                withContext(contextProvider.main) {
                    onLoading(false)
                    if (either is Either.Success) {
                        either.success.collect {
                            onSuccess(it)
                        }
                    } else if (either is Either.Failure) {
                        onError(either.failure)
                    }
                }
            } catch (e: Exception) {
                withContext(contextProvider.main) {
                    onLoading(false)
                    onError(e)
                }
            }
        }
    }
}