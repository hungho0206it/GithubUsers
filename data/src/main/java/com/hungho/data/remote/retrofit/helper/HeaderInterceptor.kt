package com.hungho.data.remote.retrofit.helper

import com.hungho.data.helper.SecretHelper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val modifiedRequest = originalRequest.newBuilder()
            .header("Content-Type", "application/json")
            .method(originalRequest.method, originalRequest.body)

        val token = SecretHelper.getAccessToken()
        modifiedRequest.addHeader(
            "Authorization",
            "Bearer $token"
        )

        return chain.proceed(modifiedRequest.build())
    }
}