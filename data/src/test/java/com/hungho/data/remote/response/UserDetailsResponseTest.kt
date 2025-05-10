package com.hungho.data.remote.response

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserDetailsResponseTest {

    @Test
    fun `toUserDetailsModel converts UserDetailsResponse to UserDetailsModel`() {
        // Given
        val userDetailsResponse = UserDetailsResponse(
            id = 1,
            login = "username",
            avatarUrl = "avatarUrl",
            blog = "Bio",
            htmlUrl = "htmlUrl",
            location = "Vietnam",
            followers = 0,
            following = 0,
        )

        // When
        val userDetailsModel = userDetailsResponse.toUserDetailsModel()

        // Then
        assertEquals(userDetailsModel.id, userDetailsResponse.id)
        assertEquals(userDetailsModel.username, userDetailsResponse.login)
        assertEquals(userDetailsModel.avatarUrl, userDetailsResponse.avatarUrl)
        assertEquals(userDetailsModel.blog, userDetailsResponse.blog)
        assertEquals(userDetailsModel.htmlUrl, userDetailsResponse.htmlUrl)
        assertEquals(userDetailsModel.location, userDetailsResponse.location)
        assertEquals(userDetailsModel.follower, userDetailsResponse.followers)
        assertEquals(userDetailsModel.following, userDetailsResponse.following)
    }
}