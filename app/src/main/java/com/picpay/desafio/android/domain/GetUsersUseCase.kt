package com.picpay.desafio.android.domain

import com.picpay.desafio.android.commons.Either
import com.picpay.desafio.android.commons.UseCase
import com.picpay.desafio.android.data.PicPayRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val repository: PicPayRepository
): UseCase<List<User>, Boolean>() {
    override suspend fun run(params: Boolean?): Either<Throwable, Flow<List<User>>> {
        return repository.getUsers(params ?: false)
    }
}