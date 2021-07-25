package com.picpay.desafio.android.data.remote

class PicPayRemoteDataSourceImpl(
    private val service: PicPayService
    ) : PicPayRemoteDataSource {
    override suspend fun getUsers(): List<UserResponse> {
        return service.getUsers()
    }
}