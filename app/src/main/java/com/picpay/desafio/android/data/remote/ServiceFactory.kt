package com.picpay.desafio.android.data.remote

import com.picpay.desafio.android.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory {
    fun getRetrofit() = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private fun getClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }
}