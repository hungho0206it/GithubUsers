package com.hungho.data.remote.retrofit.helper

import com.hungho.data.BuildConfig
import com.hungho.data.remote.retrofit.UserServices
import com.hungho.data.remote.retrofit.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal object NetworkBuilder {

    // Build OkHttp client with logging interceptor and header interceptor
    fun buildOkkHttpClient(headerInterceptor: HeaderInterceptor, isDebug: Boolean): OkHttpClient {
        val loggerInterceptor = HttpLoggingInterceptor()
        if (isDebug) {
            loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggerInterceptor)
            .addInterceptor(headerInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    // Build UserServices with Retrofit
    fun buildService(okHttpClient: OkHttpClient): UserServices {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserServices::class.java)
    }
}