package com.picpay.desafio.android.data.local

import com.picpay.desafio.android.data.local.dao.Config
import com.picpay.desafio.android.data.local.database.AppDatabase
import kotlinx.coroutines.flow.Flow

class PicPayLocalDataSourceImpl(
    private val database: AppDatabase
): PicPayLocalDataSource {
    override suspend fun getUsers(): Flow<List<UserEntity>> {
        return database.userDao().getAll()
    }

    override suspend fun updateUsers(users: List<UserEntity>) {
        database.userDao().updateAll(users)
    }

    override suspend fun getConfig(name: String): Config? {
        return database.configDao().get(name)
    }

    override suspend fun updateConfig(config: Config) {
        database.configDao().insert(config)
    }
}