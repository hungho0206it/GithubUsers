package com.hungho.githubusers.di

import com.hungho.domain.usecase.GetUserDetailsUseCase
import com.hungho.domain.usecase.GetUserPagingSourceUseCase
import com.hungho.githubusers.ui.feature.home.HomeViewModel
import com.hungho.githubusers.ui.feature.user_detail.UserDetailsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val useCaseModule = module {
    singleOf(::GetUserPagingSourceUseCase)
    singleOf(::GetUserDetailsUseCase)
}

internal val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::UserDetailsViewModel)
}