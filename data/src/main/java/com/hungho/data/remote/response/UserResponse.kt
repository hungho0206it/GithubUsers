package com.hungho.data.remote.response


import com.google.gson.annotations.SerializedName
import com.hungho.data.local.database.entity.UserEntity
import com.hungho.domain.model.UserModel

internal data class UserResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("login")
    val login: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("html_url")
    val htmlUrl: String?
) {
    fun toUserEntity(): UserEntity {
        return UserEntity(
            id = this.id ?: 0,
            loginUsername = this.login.orEmpty(),
            avatarUrl = this.avatarUrl.orEmpty(),
            htmlUrl = this.htmlUrl.orEmpty()
        )
    }

    fun toUserModel(): UserModel? {
        if (this.id == null || this.login == null) return null
        return UserModel(
            id = this.id,
            username = this.login,
            avatarUrl = this.avatarUrl.orEmpty(),
            htmlUrl = this.htmlUrl.orEmpty()
        )
    }
}