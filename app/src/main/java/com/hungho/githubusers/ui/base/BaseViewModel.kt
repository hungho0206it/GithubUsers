package com.hungho.githubusers.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hungho.data.error.Failure
import com.hungho.githubusers.ui.utils.custom.SingleLiveData

abstract class BaseViewModel : ViewModel() {
    val error = SingleLiveData<Throwable>()
    val loading = MutableLiveData<Boolean>()

    fun setLoading(isLoading: Boolean) {
        loading.postValue(isLoading)
    }

    inline fun <reified T> Result<T>.handleFailure(onFailure: (Failure) -> Unit = {}): Result<T> {
        val exception = this.exceptionOrNull()
        if (this.isFailure && exception != null) {
            setLoading(false)
            val failure = (exception as? Failure) ?: (Failure.UnknownFailure("Unknown Failure"))
            onFailure(failure)
            error.value = failure
        }
        return this
    }
}