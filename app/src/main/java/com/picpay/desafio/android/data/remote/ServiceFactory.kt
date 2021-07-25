package com.picpay.desafio.android.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory(private val baseUrl: String) {
    fun getRetrofit() = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private fun getClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }
}