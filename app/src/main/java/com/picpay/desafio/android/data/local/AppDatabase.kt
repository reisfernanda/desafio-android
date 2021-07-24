package com.picpay.desafio.android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.local.dao.Config
import com.picpay.desafio.android.data.local.dao.ConfigDao
import com.picpay.desafio.android.data.local.dao.UserDao
import com.picpay.desafio.android.domain.User

@Database(
    entities = [User::class, Config::class],
    version = AppDatabase.VERSION
)

abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
        const val FILE_NAME = "users_database.db"
        const val userTable = "user"
        const val configTable = "config"
    }

    abstract fun userDao(): UserDao

    abstract fun configDao(): ConfigDao
}