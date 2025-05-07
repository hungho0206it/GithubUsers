package com.hungho.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.hungho.data.local.database.dao.UserDao
import com.hungho.data.local.storage.AppPreferences
import com.hungho.data.remote.mediator.UserRemoteMediator
import com.hungho.data.remote.retrofit.UserServices
import com.hungho.domain.model.UserModel
import com.hungho.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class UserRepositoryImpl(
    private val userServices: UserServices,
    private val userDao: UserDao,
    private val appPreferences: AppPreferences,
) : UserRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getUserPaging(): Flow<PagingData<UserModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = UserRemoteMediator.PAGE_SIZE,
                initialLoadSize = UserRemoteMediator.PAGE_SIZE,
                prefetchDistance = UserRemoteMediator.PAGE_SIZE / 2,
                enablePlaceholders = false
            ),
            remoteMediator = UserRemoteMediator(
                userServices = userServices,
                userDao = userDao,
                appPreferences = appPreferences
            ),
            pagingSourceFactory = { userDao.getUserPagingSource() }
        ).flow.map { pagingData -> pagingData.map { it.toUserModel() } }
    }
}