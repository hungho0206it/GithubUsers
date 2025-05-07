package com.hungho.githubusers.di

import com.hungho.domain.usecase.GetUserPagingSourceUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::GetUserPagingSourceUseCase)
}

val viewModelModule = module {

}