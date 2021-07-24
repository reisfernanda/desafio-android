package com.picpay.desafio.android.data.local.dao

import androidx.room.*
import com.picpay.desafio.android.data.local.AppDatabase
import com.picpay.desafio.android.domain.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM ${AppDatabase.userTable}")
    fun getAll(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Query("DELETE FROM ${AppDatabase.userTable}")
    fun deleteAll()

    @Transaction
    fun updateAll(users: List<User>) {
        deleteAll()
        insertAll(users)
    }
}