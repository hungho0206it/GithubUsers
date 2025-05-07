package com.hungho.data.di

import com.hungho.data.local.database.AppDatabase
import com.hungho.data.local.storage.AppPreferences
import com.hungho.data.remote.retrofit.helper.HeaderInterceptor
import com.hungho.data.remote.retrofit.helper.NetworkHelper
import com.hungho.data.repository.UserRepositoryImpl
import com.hungho.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val localModule = module {
    single {
        AppDatabase.getInstance(androidContext())
    }

    single {
        get<AppDatabase>().userDao()
    }

    single {
        AppPreferences(androidContext())
    }
}

val remoteModule = module {
    single {
        HeaderInterceptor()
    }
    singleOf(NetworkHelper::buildOkkHttpClient)
    singleOf(NetworkHelper::buildService)
}

val repositoryModule = module {
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
}