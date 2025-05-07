package com.hungho.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.hungho.data.local.database.dao.UserDao
import com.hungho.data.local.database.entity.UserEntity
import com.hungho.data.local.storage.AppPreferenceKey
import com.hungho.data.local.storage.AppPreferences
import com.hungho.data.remote.retrofit.UserServices
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
internal class UserRemoteMediator(
    private val userServices: UserServices,
    private val userDao: UserDao,
    private val appPreferences: AppPreferences
) : RemoteMediator<Int, UserEntity>() {
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES)
        val lastUpdate = appPreferences.getValue(AppPreferenceKey.LONG_LAST_TIME_FETCH_USER, 0)
        return if (System.currentTimeMillis() - lastUpdate <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> START_SINCE
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                state.lastItemOrNull()?.id
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        return try {
            val response = userServices.getUsers(page, state.config.pageSize)
            val users = response.map { it.toUserEntity() }

            if (loadType == LoadType.REFRESH) {
                appPreferences.saveValue(
                    AppPreferenceKey.LONG_LAST_TIME_FETCH_USER,
                    System.currentTimeMillis()
                )
                userDao.clearAll()
            }

            userDao.insertUsers(users)

            MediatorResult.Success(endOfPaginationReached = response.size < state.config.pageSize)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        private const val START_SINCE = 0
        const val PAGE_SIZE = 20
    }
}