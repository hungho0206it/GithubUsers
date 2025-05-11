package com.hungho.data.remote.response

import com.google.gson.annotations.SerializedName
import com.hungho.domain.model.UserDetailsModel

internal data class UserDetailsResponse(
    @SerializedName("login")
    val login: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("html_url")
    val htmlUrl: String?,
    @SerializedName("blog")
    val blog: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("followers")
    val followers: Int?,
    @SerializedName("following")
    val following: Int?
) {
    fun toUserDetailsModel(): UserDetailsModel {
        return UserDetailsModel(
            id = id ?: 0,
            username = login.orEmpty(),
            avatarUrl = avatarUrl.orEmpty(),
            htmlUrl = htmlUrl.orEmpty(),
            followers = this@UserDetailsResponse.followers ?: 0,
            followings = following ?: 0,
            location = location.orEmpty(),
            blog = blog.orEmpty()
        )
    }
}