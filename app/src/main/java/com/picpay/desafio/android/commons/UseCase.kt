package com.picpay.desafio.android.commons

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<out T> {
    abstract suspend fun run(): Either<Throwable, Flow<T>>
    operator fun invoke(
        scope: CoroutineScope,
        onError: ((Throwable) -> Unit) = {},
        onSuccess: (T) -> Unit = {},
        onLoading: ((Boolean) -> Unit) = {}
    ) {

        scope.launch(Dispatchers.IO) {
            try {
                onLoading(true)
                run().run {
                    val either = this
                    withContext(Dispatchers.Main) {
                        onLoading(false)
                        if (either is Either.Success) {
                            either.success.collect {
                                onSuccess(it)
                            }
                        } else if (either is Either.Failure) {
                            onError(either.failure)
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                    onLoading(false)
                }
            }
        }
    }
}