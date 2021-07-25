package com.picpay.desafio.android.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.picpay.desafio.android.data.local.database.AppDatabase

@Entity(tableName = AppDatabase.userTable)
data class UserEntity(
    @PrimaryKey val id: Int?,
    val image: String?,
    val name: String?,
    val username: String?
)
