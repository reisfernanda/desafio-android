package com.picpay.desafio.android.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.remote.ServiceFactory
import org.koin.dsl.module

object ApplicationDI {
    val module = module {
        factory<Gson> {
            GsonBuilder().create()
        }

        factory {
            ServiceFactory(get())
        }

        factory<PicPayService> {
            get<ServiceFactory>()
                .getRetrofit()
                .create(PicPayService::class.java)
        }
    }
}