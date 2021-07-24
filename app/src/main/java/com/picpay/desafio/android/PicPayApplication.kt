package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.data.di.DataDI
import com.picpay.desafio.android.domain.di.DomainDI
import com.picpay.desafio.android.presentation.di.PresentationDI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                DataDI.module +
                        DomainDI.module +
                        PresentationDI.module
            ).androidContext(applicationContext)
        }
    }
}