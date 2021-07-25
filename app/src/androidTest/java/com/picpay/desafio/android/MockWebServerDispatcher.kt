package com.picpay.desafio.android

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockWebServerDispatcher: Dispatcher() {

    companion object {
        const val serverPort = 8080
    }

    private val successResponse by lazy {
        val body =
            "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}, " +
                    "{\"id\":1002,\"name\":\"João Silva\",\"img\":\"https://randomuser.me/api/portraits/men/1.jpg\",\"username\":\"@joao.silva\"}, " +
                    "{\"id\":1003,\"name\":\"Mário\",\"img\":\"https://randomuser.me/api/portraits/men/2.jpg\",\"username\":\"@mario\"}]"

        MockResponse()
            .setResponseCode(200)
            .setBody(body)
    }

    private val errorResponse by lazy { MockResponse().setResponseCode(404) }

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/users" -> successResponse
            else -> errorResponse
        }
    }
}