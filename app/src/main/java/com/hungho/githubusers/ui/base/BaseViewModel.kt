package com.hungho.githubusers.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hungho.githubusers.ui.utils.custom.SingleLiveData

abstract class BaseViewModel : ViewModel() {
    val error = SingleLiveData<Throwable>()
    val loading = MutableLiveData<Boolean>()

    fun setLoading(isLoading: Boolean) {
        loading.postValue(isLoading)
    }
}