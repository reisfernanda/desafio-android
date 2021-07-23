package com.picpay.desafio.android.data.local

import com.picpay.desafio.android.commons.Either
import com.picpay.desafio.android.domain.User
import kotlinx.coroutines.flow.Flow

interface PicPayLocalDataSource {
    suspend fun getUsers(): Either<Throwable, Flow<List<User>>>
}