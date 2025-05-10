package com.hungho.data.local.database.dao

import android.content.Context
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.hungho.data.local.database.AppDatabase
import com.hungho.data.local.database.entity.UserEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
class UserDaoTest {

    private lateinit var roomDatabase: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        roomDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            AppDatabase::class.java
        ).build()
        userDao = roomDatabase.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        roomDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertUsers() = runTest {
        // Given
        val userEntities = (1..10).mockUserEntities()

        // When
        userDao.insertUsers(userEntities)

        // Then
        val result = getResult()
        assertEquals(result.data, userEntities)
    }

    @Test
    @Throws(Exception::class)
    fun clearAll() = runTest {
        val expected = emptyList<UserEntity>()

        //When
        userDao.clearAll()

        //Then
        val result = getResult()
        assertEquals(result.data, expected)
    }

    private suspend fun getResult(): PagingSource.LoadResult.Page<Int, UserEntity> {
        val pager = TestPager<Int, UserEntity>(
            config = PagingConfig(20),
            pagingSource = userDao.getUserPagingSource()
        )
        val result = pager.refresh() as PagingSource.LoadResult.Page<Int, UserEntity>
        return result
    }

    private fun IntRange.mockUserEntities(): List<UserEntity> {
        return this.map {
            UserEntity(
                id = it,
                loginUsername = "username$it",
                avatarUrl = "avatarUrl$it",
                htmlUrl = "htmlUrl$it"
            )
        }
    }
}