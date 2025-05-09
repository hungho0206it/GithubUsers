package com.hungho.githubusers.ui.feature.user_detail

import androidx.lifecycle.viewModelScope
import com.hungho.domain.model.UserDetailsModel
import com.hungho.domain.usecase.GetUserDetailsUseCase
import com.hungho.githubusers.ui.base.BaseViewModel
import com.hungho.githubusers.ui.utils.custom.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UserDetailsViewModel(private val getUserDetailsUseCase: GetUserDetailsUseCase) :
    BaseViewModel() {
    val userDetails = SingleLiveData<UserDetailsModel>()

    fun getUserDetails(username: String) {
        getUserDetailsUseCase(username)
            .flowOn(Dispatchers.IO)
            .onEach {
                userDetails.postValue(it)
            }
            .launchIn(viewModelScope)
    }
}