package com.picpay.desafio.android.commons

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<out T, in Params> {
    abstract suspend fun run(params: Params? = null): Either<Throwable, Flow<T>>
    operator fun invoke(
        scope: CoroutineScope,
        params: Params? = null,
        onError: ((Throwable) -> Unit) = {},
        onSuccess: (T) -> Unit = {},
        onLoading: ((Boolean) -> Unit) = {}
    ) {

        scope.launch(Dispatchers.IO) {
            try {
                onLoading(true)
                run(params).run {
                    val either = this
                    withContext(Dispatchers.Main) {
                        onLoading(false)
                        if (either is Either.Success) {

                            either.success.collect {
                                Log.d("UseCase", "either success: $it")
                                onSuccess(it)
                            }
                        } else if (either is Either.Failure) {
                            Log.d("UseCase", "either failure: ${either.failure.localizedMessage}")
                            onError(either.failure)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("UseCase", "exception: ${e.localizedMessage}")
                withContext(Dispatchers.Main) {
                    onError(e)
                    onLoading(false)
                }
            }
        }
    }
}