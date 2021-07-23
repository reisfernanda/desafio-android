package com.picpay.desafio.android.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.picpay.desafio.android.data.local.AppDatabase
import com.picpay.desafio.android.domain.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM ${AppDatabase.userTable}")
    fun getAll(): Flow<List<User>>
}