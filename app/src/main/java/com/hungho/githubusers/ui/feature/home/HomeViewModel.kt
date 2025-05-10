package com.hungho.githubusers.ui.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hungho.domain.model.UserModel
import com.hungho.domain.usecase.GetUserPagingSourceUseCase
import com.hungho.githubusers.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class HomeViewModel(
    getUserPagingSourceUseCase: GetUserPagingSourceUseCase,
    coroutineDispatcher: CoroutineDispatcher
) : BaseViewModel() {
    val userPagingSource: Flow<PagingData<UserModel>> =
        getUserPagingSourceUseCase().cachedIn(viewModelScope).flowOn(coroutineDispatcher)
}