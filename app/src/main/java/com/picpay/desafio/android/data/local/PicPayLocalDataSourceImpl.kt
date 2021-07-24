package com.picpay.desafio.android.data.local

import com.picpay.desafio.android.data.local.dao.Config
import com.picpay.desafio.android.domain.User
import kotlinx.coroutines.flow.Flow

class PicPayLocalDataSourceImpl(
    private val database: AppDatabase
): PicPayLocalDataSource {
    override suspend fun getUsers(): Flow<List<User>> {
        return database.userDao().getAll()
    }

    override suspend fun updateUsers(users: List<User>) {
        database.userDao().updateAll(users)
    }

    override suspend fun getConfig(name: String): Config? {
        return database.configDao().get(name)
    }

    override suspend fun updateConfig(config: Config) {
        database.configDao().insert(config)
    }
}