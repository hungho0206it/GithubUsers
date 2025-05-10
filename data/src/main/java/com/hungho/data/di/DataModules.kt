package com.hungho.data.di

import com.hungho.data.BuildConfig
import com.hungho.data.helper.AndroidKeyStoreProvider
import com.hungho.data.helper.BuildFlavor
import com.hungho.data.helper.EncryptedPrefsHelper
import com.hungho.data.helper.FlavorHelper
import com.hungho.data.helper.IEncrypted
import com.hungho.data.helper.ISecretHelper
import com.hungho.data.helper.KeyProvider
import com.hungho.data.helper.SecretHelper
import com.hungho.data.local.database.AppDatabase
import com.hungho.data.local.storage.AppPreferences
import com.hungho.data.remote.retrofit.helper.HeaderInterceptor
import com.hungho.data.remote.retrofit.helper.NetworkHelper
import com.hungho.data.repository.UserRepositoryImpl
import com.hungho.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatcherModule = module {
    single(named("dispatcherIO")) {
        Dispatchers.IO
    }
    single(named("dispatcherDefault")) {
        Dispatchers.Default
    }
    single(named("dispatcherMain")) {
        Dispatchers.Default
    }
    single(named("dispatcherImmediate")) {
        Dispatchers.Main.immediate
    }
}

val helperModule = module {
    single {
        FlavorHelper(BuildFlavor)
    }
    singleOf(::SecretHelper) { bind<ISecretHelper>() }
    singleOf(::AndroidKeyStoreProvider) { bind<KeyProvider>() }
    singleOf(::EncryptedPrefsHelper) { bind<IEncrypted>() }
}

val localModule = module {
    singleOf(AppDatabase::getInstance)
    single {
        AppPreferences(androidContext(), get(), get(named("dispatcherIO")))
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