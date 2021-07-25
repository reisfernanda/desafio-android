package com.picpay.desafio.android.data.local

import com.picpay.desafio.android.data.local.dao.Config
import kotlinx.coroutines.flow.Flow

interface PicPayLocalDataSource {
    suspend fun getUsers(): Flow<List<UserEntity>>

    suspend fun updateUsers(users: List<UserEntity>)

    suspend fun getConfig(name: String): Config?

    suspend fun updateConfig(config: Config)
}