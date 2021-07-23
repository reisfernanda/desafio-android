package com.picpay.desafio.android.domain

import com.picpay.desafio.android.commons.Either
import com.picpay.desafio.android.commons.UseCase
import com.picpay.desafio.android.data.PicPayRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val repository: PicPayRepository
): UseCase<List<User>>() {
    override suspend fun run(): Either<Throwable, Flow<List<User>>> {
        return repository.getUsers()
    }
}