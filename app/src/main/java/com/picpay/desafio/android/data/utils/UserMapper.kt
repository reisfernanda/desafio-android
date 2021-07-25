package com.picpay.desafio.android.data.utils

import com.picpay.desafio.android.data.local.UserEntity
import com.picpay.desafio.android.data.remote.UserResponse
import com.picpay.desafio.android.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun UserResponse.toEntity(): UserEntity {
    return UserEntity(id = id, name = name, username = username, image = image)
}

fun UserEntity.toDomain(): User {
    return User(
        name = name ?: "",
        username = username ?: "",
        image = image ?: ""
    )
}

fun List<UserResponse>.toEntityList(): List<UserEntity> {
    return filter {
        it.id != null && it.username != null
    }.map {
        it.toEntity()
    }
}

fun Flow<List<UserEntity>>.toDomainList(): Flow<List<User>> {
    return map { list -> list.map { it.toDomain() } }
}
