package com.hungho.githubusers.ui.feature.user_detail

import androidx.lifecycle.viewModelScope
import com.hungho.data.helper.toFailure
import com.hungho.domain.model.UserDetailsModel
import com.hungho.domain.usecase.GetUserDetailsUseCase
import com.hungho.githubusers.ui.base.BaseViewModel
import com.hungho.githubusers.ui.utils.custom.SingleLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UserDetailsViewModel(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
) : BaseViewModel() {
    val userDetails = SingleLiveData<UserDetailsModel>()

    fun getUserDetails(username: String) {
        setLoading(true)
        getUserDetailsUseCase(username)
            .flowOn(coroutineDispatcher)
            .catch {
                setLoading(false)
                error.postValue(it.toFailure())
            }
            .onEach {
                setLoading(false)
                userDetails.postValue(it)
            }
            .launchIn(viewModelScope)
    }
}