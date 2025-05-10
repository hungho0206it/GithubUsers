package com.hungho.githubusers.di

import com.hungho.domain.usecase.GetUserDetailsUseCase
import com.hungho.domain.usecase.GetUserPagingSourceUseCase
import com.hungho.githubusers.ui.feature.home.HomeViewModel
import com.hungho.githubusers.ui.feature.user_detail.UserDetailsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::GetUserPagingSourceUseCase)
    singleOf(::GetUserDetailsUseCase)
}

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(), get(named("dispatcherIO")))
    }
    viewModel {
        UserDetailsViewModel(get(), get(named("dispatcherIO")))
    }
}