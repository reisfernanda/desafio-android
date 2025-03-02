package com.picpay.desafio.android.data

import com.picpay.desafio.android.commons.Either
import com.picpay.desafio.android.domain.User
import kotlinx.coroutines.flow.Flow

interface PicPayRepository {
    suspend fun getUsers(forceUpdate: Boolean): Either<Throwable, Flow<List<User>>>
}