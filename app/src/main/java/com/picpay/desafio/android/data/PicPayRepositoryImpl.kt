package com.picpay.desafio.android.data

import com.picpay.desafio.android.commons.Either
import com.picpay.desafio.android.data.remote.PicPayRemoteDataSource
import com.picpay.desafio.android.domain.User
import kotlinx.coroutines.flow.Flow

class PicPayRepositoryImpl(
    private val remoteDataSource: PicPayRemoteDataSource
): PicPayRepository {
    override suspend fun getUsers(): Flow<Either<Throwable, List<User>>> {
        return remoteDataSource.getUsers()
    }
}