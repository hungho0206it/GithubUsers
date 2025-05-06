package com.hungho.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hungho.domain.model.UserModel

@Entity(tableName = "users")
internal data class UserEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "username")
    val loginUsername: String,
    @ColumnInfo(name = "avatar")
    val avatarUrl: String,
    @ColumnInfo(name = "htmlUrl")
    val htmlUrl: String
) {
    fun toUserModel(): UserModel {
        return UserModel(
            id = id,
            username = loginUsername,
            avatarUrl = avatarUrl,
            htmlUrl = htmlUrl
        )
    }
}