package com.hungho.data.local.database.entity

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserEntityTest {

    @Test
    fun `toUserModel converts UserEntity to UserModel`() {
        // Given
        val userEntity = UserEntity(
            id = 1,
            loginUsername = "username",
            avatarUrl = "avatarUrl",
            htmlUrl = "htmlUrl"
        )

        // When
        val userModel = userEntity.toUserModel()

        //Then
        assertEquals(userModel.id, userEntity.id)
        assertEquals(userModel.username, userEntity.loginUsername)
        assertEquals(userModel.avatarUrl, userEntity.avatarUrl)
        assertEquals(userModel.htmlUrl, userEntity.htmlUrl)
    }
}