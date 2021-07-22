package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.PicPayRepository
import com.picpay.desafio.android.data.PicPayRepositoryImpl
import com.picpay.desafio.android.data.remote.*
import com.picpay.desafio.android.domain.GetUsersUseCase
import com.picpay.desafio.android.ui.UserListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ApplicationDI {
    val module = module {
        factory {
            ServiceFactory()
        }

        factory<PicPayService> {
            get<ServiceFactory>()
                .getRetrofit()
                .create(PicPayService::class.java)
        }

        factory {
            RequestWrapper()
        }

        factory<PicPayRemoteDataSource> {
            PicPayRemoteDataSourceImpl(get(), get())
        }

        factory<PicPayRepository> {
            PicPayRepositoryImpl(get())
        }

        factory {
            GetUsersUseCase(get())
        }

        viewModel { UserListViewModel(androidApplication(), get()) }
    }
}