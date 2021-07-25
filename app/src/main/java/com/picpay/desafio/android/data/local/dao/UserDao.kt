package com.picpay.desafio.android.data.local.dao

import androidx.room.*
import com.picpay.desafio.android.data.local.database.AppDatabase
import com.picpay.desafio.android.data.local.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM ${AppDatabase.userTable}")
    fun getAll(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserEntity>)

    @Query("DELETE FROM ${AppDatabase.userTable}")
    fun deleteAll()

    @Transaction
    fun updateAll(users: List<UserEntity>) {
        deleteAll()
        insertAll(users)
    }
}