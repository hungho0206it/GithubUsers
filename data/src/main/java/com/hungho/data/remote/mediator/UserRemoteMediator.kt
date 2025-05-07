package com.hungho.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.hungho.data.local.database.dao.UserDao
import com.hungho.data.local.database.entity.UserEntity
import com.hungho.data.remote.retrofit.UserServices

@OptIn(ExperimentalPagingApi::class)
internal class UserRemoteMediator(
    private val userServices: UserServices,
    private val userDao: UserDao
) : RemoteMediator<Int, UserEntity>() {
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