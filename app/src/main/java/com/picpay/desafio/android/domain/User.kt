package com.picpay.desafio.android.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.picpay.desafio.android.data.local.AppDatabase

@Entity(tableName = AppDatabase.userTable)
data class User(
    @PrimaryKey val id: Int,
    val img: String,
    val name: String,
    val username: String
)