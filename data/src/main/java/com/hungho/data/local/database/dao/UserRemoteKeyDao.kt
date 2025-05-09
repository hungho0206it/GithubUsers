package com.hungho.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hungho.data.local.database.entity.UserRemoteKeyEntity

@Dao
internal interface UserRemoteKeyDao {
    @Query("SELECT * FROM user_remote_keys WHERE userId = :userId")
    suspend fun getRemoteKeyByUserId(userId: Int): UserRemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<UserRemoteKeyEntity>)

    @Query("DELETE FROM user_remote_keys")
    suspend fun clearAll()
}