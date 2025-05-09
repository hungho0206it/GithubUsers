package com.hungho.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.hungho.data.local.database.AppDatabase
import com.hungho.data.local.storage.AppPreferences
import com.hungho.data.remote.mediator.UserRemoteMediator
import com.hungho.data.remote.retrofit.UserServices
import com.hungho.domain.model.UserDetailsModel
import com.hungho.domain.model.UserModel
import com.hungho.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class UserRepositoryImpl(
    private val userServices: UserServices,
    private val database: AppDatabase,
    private val appPreferences: AppPreferences,
) : UserRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getUserPaging(): Flow<PagingData<UserModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = UserRemoteMediator.PAGE_SIZE,
                initialLoadSize = UserRemoteMediator.PAGE_SIZE,
                prefetchDistance = 5,
            ),
            remoteMediator = UserRemoteMediator(
                userServices = userServices,
                database = database,
                appPreferences = appPreferences
            ),
            pagingSourceFactory = { database.userDao().getUserPagingSource() }
        ).flow.map { pagingData -> pagingData.map { it.toUserModel() } }
    }

    override fun getUserDetails(username: String): Flow<UserDetailsModel> {
        return flow {
            val response = userServices.getUserDetails(username)
            emit(response.toUserDetailsModel())
        }
    }
}