package com.picpay.desafio.android.data.di

import androidx.room.Room
import com.picpay.desafio.android.data.PicPayRepository
import com.picpay.desafio.android.data.PicPayRepositoryImpl
import com.picpay.desafio.android.data.local.AppDatabase
import com.picpay.desafio.android.data.local.PicPayLocalDataSource
import com.picpay.desafio.android.data.local.PicPayLocalDataSourceImpl
import com.picpay.desafio.android.data.remote.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DataDI {
    val module = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                AppDatabase.FILE_NAME
            ).fallbackToDestructiveMigration()
                .build()
        }

        factory {
            ServiceFactory()
        }

        factory<PicPayService> {
            get<ServiceFactory>()
                .getRetrofit()
                .create(PicPayService::class.java)
        }

        factory {
            Wrapper()
        }

        factory<PicPayRemoteDataSource> {
            PicPayRemoteDataSourceImpl(get())
        }

        factory<PicPayLocalDataSource> {
            PicPayLocalDataSourceImpl(get())
        }

        factory<PicPayRepository> {
            PicPayRepositoryImpl(get(), get(), get())
        }
    }
}