package com.hungho.githubusers.di

import com.hungho.domain.usecase.GetUserPagingSourceUseCase
import com.hungho.githubusers.ui.feature.home.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::GetUserPagingSourceUseCase)
}

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
}