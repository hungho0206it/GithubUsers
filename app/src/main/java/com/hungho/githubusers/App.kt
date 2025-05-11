package com.hungho.githubusers

import android.app.Application
import com.hungho.data.di.dataModules
import com.hungho.githubusers.di.useCaseModule
import com.hungho.githubusers.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
        initKoin()
    }

    private fun initLogger() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    dataModules,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}