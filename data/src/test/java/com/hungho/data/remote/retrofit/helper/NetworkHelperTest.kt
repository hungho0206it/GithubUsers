package com.hungho.data.remote.retrofit.helper

import com.hungho.data.BuildConfig
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NetworkHelperTest {

    private lateinit var headerInterceptor: HeaderInterceptor

    @Before
    fun setup() {
        headerInterceptor = mockk(relaxed = true)
    }

    @Test
    fun `buildOkkHttpClient should contain header and logging interceptors`() {
        // Given
        val expectedLevel = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE

        // When
        val client = NetworkHelper.buildOkkHttpClient(headerInterceptor)

        // Then
        val interceptors = client.interceptors
        val logging = interceptors.find { it is HttpLoggingInterceptor } as? HttpLoggingInterceptor

        assertTrue(interceptors.any { it is HttpLoggingInterceptor })
        assertTrue(interceptors.any { it == headerInterceptor })

        assertEquals(expectedLevel, logging?.level)
    }

    @Test
    fun `buildService should return valid retrofit service`() {
        val client = OkHttpClient.Builder().build()
        val service = NetworkHelper.buildService(client)

        assertNotNull(service)
    }
}