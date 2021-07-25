package com.picpay.desafio.android.data

import android.util.Log
import com.picpay.desafio.android.commons.Either
import com.picpay.desafio.android.data.local.PicPayLocalDataSource
import com.picpay.desafio.android.data.local.dao.Config
import com.picpay.desafio.android.data.remote.PicPayRemoteDataSource
import com.picpay.desafio.android.data.remote.UserResponse
import com.picpay.desafio.android.data.utils.CacheStrategy
import com.picpay.desafio.android.data.utils.Wrapper
import com.picpay.desafio.android.data.utils.toDomainList
import com.picpay.desafio.android.data.utils.toEntityList
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
        var usersFromRemote: Either<Throwable, Flow<List<UserResponse>>>? = null

        if (forceUpdate || shouldCallRemote()) {
            usersFromRemote = wrapper.wrap {
                flow { emit (remoteDataSource.getUsers())}

                // For testing purpose
//                flow { emit(listOf(UserResponse(11, "", "José", "jose"),
//                    UserResponse(5, "", "João Silva!!", "joaos"),
//                    UserResponse(4, "", "Fernanda", "fernandar"),
//                    UserResponse(333, "", "Maria", "mariav"))) }
            }
        }

        return wrapper.wrap {
            if (usersFromRemote is Either.Success) {
                usersFromRemote.success.collect {
                    localDataSource.updateUsers(it.toEntityList())

                    setLastUpdate()
                    Log.d(TAG, "Users updated on database: $it")
                }
            } else {
                Log.d(TAG, "Failed to get users from remote.")
            }
            localDataSource.getUsers().toDomainList()
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