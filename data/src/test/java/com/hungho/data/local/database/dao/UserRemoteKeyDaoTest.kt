package com.hungho.data.local.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hungho.data.local.database.AppDatabase
import com.hungho.data.local.database.entity.UserRemoteKeyEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
class UserRemoteKeyDaoTest {

    private lateinit var roomDatabase: AppDatabase
    private lateinit var userRemoteKeyDao: UserRemoteKeyDao

    @Before
    fun setup() {
        roomDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            AppDatabase::class.java
        ).build()
        userRemoteKeyDao = roomDatabase.userRemoteKeyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        roomDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAll() = runTest {
        // Given
        val userRemoteKeyEntities = (1..10).mockUserRemoteKeyEntities()

        // When
        userRemoteKeyDao.insertAll(userRemoteKeyEntities)

        // Then
        val result = getResult(userRemoteKeyEntities.first().userId)
        assertEquals(result, userRemoteKeyEntities.first())
    }

    @Test
    @Throws(Exception::class)
    fun clearAll() = runTest {
        // Given
        val firstList = (1..10).mockUserRemoteKeyEntities()
        userRemoteKeyDao.insertAll(firstList)

        //When
        userRemoteKeyDao.clearAll()

        //Then
        val result = getResult(firstList.first().userId)
        assert(result == null)
    }

    private suspend fun getResult(userId: Int): UserRemoteKeyEntity? {
        return userRemoteKeyDao.getRemoteKeyByUserId(userId)
    }

    private fun IntRange.mockUserRemoteKeyEntities(): List<UserRemoteKeyEntity> {
        return this.map {
            UserRemoteKeyEntity(
                userId = it,
                prevKey = null,
                nextKey = it + 20
            )
        }
    }
}