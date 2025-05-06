package com.hungho.domain.usecase

import com.hungho.domain.model.UserModel
import com.hungho.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserListUseCase(private val userRepository: UserRepository) {
    operator fun invoke(since: Int, perPage: Int): Flow<List<UserModel>> {
        return userRepository.getUsers(since = since, perPage = perPage)
    }
}