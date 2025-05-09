package com.hungho.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.hungho.data.local.database.AppDatabase
import com.hungho.data.local.database.entity.UserEntity
import com.hungho.data.local.database.entity.UserRemoteKeyEntity
import com.hungho.data.local.storage.AppPreferenceKey
import com.hungho.data.local.storage.AppPreferences
import com.hungho.data.remote.retrofit.UserServices
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
internal class UserRemoteMediator(
    private val database: AppDatabase,
    private val userServices: UserServices,
    private val appPreferences: AppPreferences,
) : RemoteMediator<Int, UserEntity>() {
    private val userDao = database.userDao()
    private val remoteKeyDao = database.userRemoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES)
        val lastUpdate = appPreferences.getValue(
            AppPreferenceKey.LONG_LAST_TIME_FETCH_USER,
            0L
        )
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
        val since = when (loadType) {
            LoadType.REFRESH -> START_SINCE
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(false)
                val remoteKey = remoteKeyDao.getRemoteKeyByUserId(lastItem.id)
                remoteKey?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        return try {
            val response = userServices.getUsers(since, state.config.pageSize)
            val users = response.map { it.toUserEntity() }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appPreferences.saveValue(
                        AppPreferenceKey.LONG_LAST_TIME_FETCH_USER,
                        System.currentTimeMillis()
                    )
                    userDao.clearAll()
                    remoteKeyDao.clearAll()
                }

                userDao.insertUsers(users)

                val keys = users.map {
                    UserRemoteKeyEntity(
                        userId = it.id,
                        prevKey = null,
                        nextKey = users.lastOrNull()?.id
                    )
                }
                remoteKeyDao.insertAll(keys)
            }

            MediatorResult.Success(endOfPaginationReached = users.size < state.config.pageSize)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        private const val START_SINCE = 0
        const val PAGE_SIZE = 20
    }
}