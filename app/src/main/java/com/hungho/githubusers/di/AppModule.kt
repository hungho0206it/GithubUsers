package com.hungho.githubusers.di

import com.hungho.domain.usecase.GetUserListUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::GetUserListUseCase)
}

val viewModelModule = module {

}