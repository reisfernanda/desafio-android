package com.picpay.desafio.android.di

import androidx.room.Room
import com.picpay.desafio.android.data.PicPayRepository
import com.picpay.desafio.android.data.PicPayRepositoryImpl
import com.picpay.desafio.android.data.local.AppDatabase
import com.picpay.desafio.android.data.local.PicPayLocalDataSource
import com.picpay.desafio.android.data.local.PicPayLocalDataSourceImpl
import com.picpay.desafio.android.data.remote.*
import com.picpay.desafio.android.domain.GetUsersUseCase
import com.picpay.desafio.android.ui.UserListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ApplicationDI {
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

        factory {
            GetUsersUseCase(get())
        }

        viewModel { UserListViewModel(androidApplication(), get()) }
    }
}