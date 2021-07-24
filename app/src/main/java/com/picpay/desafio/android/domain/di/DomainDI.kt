package com.picpay.desafio.android.domain.di

import com.picpay.desafio.android.domain.GetUsersUseCase
import org.koin.dsl.module

object DomainDI {
    val module = module {
        factory {
            GetUsersUseCase(get())
        }
    }
}