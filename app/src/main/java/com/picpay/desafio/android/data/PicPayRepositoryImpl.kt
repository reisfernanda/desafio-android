package com.picpay.desafio.android.data

import android.util.Log
import com.picpay.desafio.android.commons.Either
import com.picpay.desafio.android.data.local.PicPayLocalDataSource
import com.picpay.desafio.android.data.local.dao.Config
import com.picpay.desafio.android.data.remote.PicPayRemoteDataSource
import com.picpay.desafio.android.data.remote.Wrapper
import com.picpay.desafio.android.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

private const val TAG = "PicPayRepositoryImpl"

class PicPayRepositoryImpl(
    private val wrapper: Wrapper,
    private val remoteDataSource: PicPayRemoteDataSource,
    private val localDataSource: PicPayLocalDataSource
): PicPayRepository {
    private val lastUpdate = "lastUpdate"

    override suspend fun getUsers(forceUpdate: Boolean): Either<Throwable, Flow<List<User>>> {
        var usersFromRemote: Either<Throwable, Flow<List<User>>>? = null

        if (forceUpdate || shouldCallRemote()) {
            usersFromRemote = wrapper.wrap {
                flow { emit (remoteDataSource.getUsers())}

                // For testing purpose
//                flow { emit(listOf(User(11, "", "José", "jose"),
//                    User(5, "", "João Silva!!", "joaos"),
//                    User(4, "", "Fernanda", "fernandar"),
//                    User(333, "", "Maria", "mariav"))) }
            }
        }

        return wrapper.wrap {
            if (usersFromRemote is Either.Success) {
                usersFromRemote.success.collect {
                    localDataSource.updateUsers(it)

                    setLastUpdate()
                    Log.d(TAG, "Users updated on database: $it")
                }
            } else {
                Log.d(TAG, "Failed to get users from remote.")
            }
            localDataSource.getUsers()
        }
    }

    private suspend fun shouldCallRemote(): Boolean {
        val lastUpdateValue: Long? = localDataSource.getConfig(lastUpdate)?.value
        return CacheStrategy.isCacheExpired(lastUpdateValue)
    }

    private suspend fun setLastUpdate() {
        localDataSource.updateConfig(Config(lastUpdate, System.currentTimeMillis()))
    }
}