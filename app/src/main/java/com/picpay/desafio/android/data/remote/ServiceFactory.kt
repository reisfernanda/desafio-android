package com.picpay.desafio.android.data.remote

import com.google.gson.Gson
import com.picpay.desafio.android.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory(private val gson: Gson) {
    fun getRetrofit() = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private fun getClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }
}