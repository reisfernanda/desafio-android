package com.picpay.desafio.android.commons

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<T> {
    abstract suspend fun run(): Flow<Either<Throwable, T>>
    operator fun invoke(
        scope: CoroutineScope,
        onError: ((Throwable) -> Unit) = {},
        onSuccess: (T) -> Unit = {},
        onLoading: ((Boolean) -> Unit) = {}
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                onLoading(true)
                run().collect {
                    withContext(Dispatchers.Main) {
                        onLoading(false)
                        it.either(
                            onSuccess = { onSuccess(it) },
                            onFailure = { onError(it) }
                        )
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