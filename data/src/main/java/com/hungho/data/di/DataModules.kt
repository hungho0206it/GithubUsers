package com.hungho.data.di

import com.hungho.data.BuildConfig
import com.hungho.data.helper.AndroidKeyStoreProvider
import com.hungho.data.helper.BuildFlavor
import com.hungho.data.helper.EncryptedProviderPrefsHelper
import com.hungho.data.helper.FlavorHelper
import com.hungho.data.helper.ISecretHelper
import com.hungho.data.helper.SecretHelper
import com.hungho.data.local.database.AppDatabase
import com.hungho.data.local.storage.AppPreferences
import com.hungho.data.local.storage.AppPreferencesImpl
import com.hungho.data.provider.DispatcherProviderImpl
import com.hungho.data.remote.retrofit.helper.HeaderInterceptor
import com.hungho.data.remote.retrofit.helper.NetworkHelper
import com.hungho.data.repository.UserRepositoryImpl
import com.hungho.domain.provider.DispatcherProvider
import com.hungho.domain.provider.EncryptedProvider
import com.hungho.domain.provider.KeyProvider
import com.hungho.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dispatcherModule = module {
    singleOf(::DispatcherProviderImpl) { bind<DispatcherProvider>() }
}

val helperModule = module {
    single {
        FlavorHelper(BuildFlavor)
    }
    singleOf(::SecretHelper) { bind<ISecretHelper>() }
    singleOf(::AndroidKeyStoreProvider) { bind<KeyProvider>() }
    singleOf(::EncryptedProviderPrefsHelper) { bind<EncryptedProvider>() }
}

val localModule = module {
    singleOf(AppDatabase::getInstance)
    single<AppPreferences> {
        AppPreferencesImpl(androidContext(), get(), get())
    }
}

val remoteModule = module {
    singleOf(::HeaderInterceptor)
    single {
        NetworkHelper.buildOkkHttpClient(get(), BuildConfig.DEBUG)
    }
    singleOf(NetworkHelper::buildService)
}

val repositoryModule = module {
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
}