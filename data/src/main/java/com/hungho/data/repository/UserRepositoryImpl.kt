package com.hungho.data.repository

import com.hungho.data.local.database.dao.UserDao
import com.hungho.data.remote.retrofit.UserServices
import com.hungho.domain.model.UserModel
import com.hungho.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class UserRepositoryImpl(
    private val userServices: UserServices,
    private val userDao: UserDao
) : UserRepository {
    override fun getUsers(since: Int, perPage: Int): Flow<List<UserModel>> = flow {
        userServices.getUsers(since = since, perPage = perPage)
    }
}