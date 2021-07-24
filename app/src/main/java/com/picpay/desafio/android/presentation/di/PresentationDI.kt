package com.picpay.desafio.android.presentation.di

import com.picpay.desafio.android.presentation.UserListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationDI {
    val module = module {
        viewModel { UserListViewModel(androidApplication(), get()) }
    }
}