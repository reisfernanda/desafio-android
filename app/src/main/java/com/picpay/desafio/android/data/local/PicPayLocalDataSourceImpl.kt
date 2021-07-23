package com.picpay.desafio.android.data.local

import com.picpay.desafio.android.commons.Either
import com.picpay.desafio.android.data.remote.RequestWrapper
import com.picpay.desafio.android.domain.User
import kotlinx.coroutines.flow.Flow

class PicPayLocalDataSourceImpl(
    private val requestWrapper: RequestWrapper,
    private val database: AppDatabase
): PicPayLocalDataSource {
    override suspend fun getUsers(): Either<Throwable, Flow<List<User>>> {
        return requestWrapper.wrapper {  database.userDao().getAll() }
    }
}