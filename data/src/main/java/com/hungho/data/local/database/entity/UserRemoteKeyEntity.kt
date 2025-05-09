package com.hungho.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_remote_keys")
data class UserRemoteKeyEntity(
    @PrimaryKey val userId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)