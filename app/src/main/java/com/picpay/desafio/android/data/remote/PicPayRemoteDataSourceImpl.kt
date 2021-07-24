package com.picpay.desafio.android.data.remote

import com.picpay.desafio.android.domain.User

class PicPayRemoteDataSourceImpl(
    private val service: PicPayService
    ) : PicPayRemoteDataSource {
    override suspend fun getUsers(): List<User> {
        return service.getUsers()
    }
}