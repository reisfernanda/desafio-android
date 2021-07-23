package com.picpay.desafio.android.data.remote

import com.picpay.desafio.android.commons.Either
import com.picpay.desafio.android.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PicPayRemoteDataSourceImpl(
    private val service: PicPayService,
    private val requestWrapper: RequestWrapper
    ) : PicPayRemoteDataSource {
    override suspend fun getUsers(): Either<Throwable, Flow<List<User>>> {
        return requestWrapper.wrapper { flow { emit( service.getUsers()) } }
    }
}