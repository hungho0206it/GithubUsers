package com.hungho.domain.repository

import com.hungho.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(since: Int, perPage: Int): Flow<List<UserModel>>
}