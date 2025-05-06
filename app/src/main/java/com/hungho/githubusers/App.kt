package com.hungho.githubusers

import android.app.Application
import com.hungho.data.di.localModule
import com.hungho.data.di.remoteModule
import com.hungho.data.di.repositoryModule
import com.hungho.githubusers.di.useCaseModule
import com.hungho.githubusers.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    localModule,
                    remoteModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}