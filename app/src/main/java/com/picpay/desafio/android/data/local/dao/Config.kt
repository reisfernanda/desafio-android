package com.picpay.desafio.android.data.local.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.picpay.desafio.android.data.local.AppDatabase

@Entity(tableName = AppDatabase.configTable)
data class Config(
    @PrimaryKey val name: String,
    val value: Long?,
)