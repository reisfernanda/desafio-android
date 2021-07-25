package com.picpay.desafio.android

import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.remote.ServiceFactory
import org.koin.dsl.module

object TestDI {
    val mockServiceModule = module {
        factory {
            ServiceFactory("http://127.0.0.1:8080")
        }

        factory<PicPayService> {
            get<ServiceFactory>()
                .getRetrofit()
                .create(PicPayService::class.java)
        }
    }
}