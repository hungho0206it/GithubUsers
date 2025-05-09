package com.hungho.data.remote.retrofit

import com.hungho.data.remote.response.UserDetailsResponse
import com.hungho.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface UserServices {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int = 0,
        @Query("per_page") perPage: Int = 20
    ): List<UserResponse>

    @GET("users/{username}")
    suspend fun getUserDetails(@Path("username") username: String): UserDetailsResponse
}