package com.picpay.desafio.android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.data.local.database.AppDatabase

@Dao
interface ConfigDao {

    @Query("SELECT * FROM ${AppDatabase.configTable} WHERE name= :name")
    fun get(name: String): Config?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(config: Config)
}