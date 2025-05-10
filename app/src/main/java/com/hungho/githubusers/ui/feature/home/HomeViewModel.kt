package com.hungho.githubusers.ui.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hungho.domain.model.UserModel
import com.hungho.domain.usecase.GetUserPagingSourceUseCase
import com.hungho.githubusers.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    getUserPagingSourceUseCase: GetUserPagingSourceUseCase,
    coroutineContext: CoroutineContext
) :
    BaseViewModel() {
    val userPagingSource: Flow<PagingData<UserModel>> =
        getUserPagingSourceUseCase().cachedIn(viewModelScope).flowOn(coroutineContext)
}