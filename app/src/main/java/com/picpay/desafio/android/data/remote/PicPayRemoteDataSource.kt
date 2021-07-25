package com.picpay.desafio.android.data.remote

interface PicPayRemoteDataSource {
    suspend fun getUsers(): List<UserResponse>
}