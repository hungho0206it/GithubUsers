package com.hungho.domain.usecase

import com.hungho.domain.repository.UserRepository

class GetUserDetailsUseCase(private val userRepository: UserRepository) {
    operator fun invoke(username: String) = userRepository.getUserDetails(username)
}