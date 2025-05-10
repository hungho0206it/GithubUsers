package com.hungho.data.helper

import com.google.gson.JsonParseException
import com.hungho.data.error.Failure
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.json.JSONException
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ThrowableExtTest {
    @Test
    fun `HttpException with 401 returns UnauthorizedFailure`() {
        // Given
        val response = Response.error<String>(401, ResponseBody.create("application/json".toMediaType(), ""))
        val exception = HttpException(response)

        val result = exception.toFailure()

        assertTrue(result is Failure.UnauthorizedFailure)
    }

    @Test
    fun `HttpException with non-401 and error body returns ApiFailure`() {
        // Given
        val responseBody = ResponseBody.create("application/json".toMediaType(), "Some error")
        val response = Response.error<String>(500, responseBody)
        val exception = HttpException(response)

        // When
        val result = exception.toFailure()

        // Then
        assertTrue(result is Failure.ApiFailure)
        assertEquals(500, (result as Failure.ApiFailure).code)
    }

    @Test
    fun `IOException returns NetworkFailure`() {
        // Given
        val exception = IOException("Connection lost")

        // When
        val result = exception.toFailure()

        // Then
        assertTrue(result is Failure.NetworkFailure)
    }

    @Test
    fun `JsonParseException returns ResponseParserFailure`() {
        // Given
        val exception = JsonParseException("Malformed JSON")

        // When
        val result = exception.toFailure()

        // Then
        assertTrue(result is Failure.ResponseParserFailure)
    }

    @Test
    fun `JSONException returns ResponseParserFailure`() {
        // Given
        val exception = JSONException("Invalid JSON")

        // When
        val result = exception.toFailure()

        // Then
        assertTrue(result is Failure.ResponseParserFailure)
    }

    @Test
    fun `Unknown exception returns UnknownFailure`() {
        // Given
        val exception = IllegalStateException("Something went wrong")

        // When
        val result = exception.toFailure()

        // Then
        assertTrue(result is Failure.UnknownFailure)
    }
}