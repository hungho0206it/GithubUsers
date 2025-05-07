package com.hungho.domain.usecase

import androidx.paging.PagingData
import com.hungho.domain.model.UserModel
import com.hungho.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserPagingSourceUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Flow<PagingData<UserModel>> {
        return userRepository.getUserPaging()
    }
}