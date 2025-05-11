package com.hungho.domain.model

data class UserDetailsModel(
    val id: Int,
    val username: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val followers: Int,
    val followings: Int,
    val location: String,
    val blog: String
)