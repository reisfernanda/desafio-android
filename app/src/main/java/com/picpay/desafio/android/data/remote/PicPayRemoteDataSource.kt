package com.picpay.desafio.android.data.remote

import com.picpay.desafio.android.commons.Either
import com.picpay.desafio.android.domain.User
import kotlinx.coroutines.flow.Flow

interface PicPayRemoteDataSource {
    suspend fun getUsers(): Either<Throwable, Flow<List<User>>>
}