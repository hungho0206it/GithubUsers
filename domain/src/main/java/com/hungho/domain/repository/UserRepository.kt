package com.hungho.domain.repository

import androidx.paging.PagingData
import com.hungho.domain.model.UserDetailsModel
import com.hungho.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserPaging(): Flow<PagingData<UserModel>>

    fun getUserDetails(username: String): Flow<UserDetailsModel>
}