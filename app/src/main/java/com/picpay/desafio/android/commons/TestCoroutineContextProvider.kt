package com.picpay.desafio.android.commons

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestCoroutineContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Dispatchers.Unconfined
    override val io: CoroutineContext = Dispatchers.Unconfined
}