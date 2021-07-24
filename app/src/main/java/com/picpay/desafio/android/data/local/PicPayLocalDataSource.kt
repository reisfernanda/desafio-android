package com.picpay.desafio.android.data.local

import com.picpay.desafio.android.data.local.dao.Config
import com.picpay.desafio.android.domain.User
import kotlinx.coroutines.flow.Flow

interface PicPayLocalDataSource {
    suspend fun getUsers(): Flow<List<User>>

    suspend fun updateUsers(users: List<User>)

    suspend fun getConfig(name: String): Config?

    suspend fun updateConfig(config: Config)
}