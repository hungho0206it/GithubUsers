package com.hungho.data.helper

import com.google.gson.JsonParseException
import com.hungho.data.helper.extension.toFailure
import com.hungho.domain.model.error.Failure
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONException
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ThrowableExtTest {
    @Test
    fun `HttpException with 401 returns UnauthorizedFailure`() {
        // Given
        val response = Response.error<String>(401, "Unauthorized".toResponseBody())
        val exception = HttpException(response)

        val result = exception.toFailure()

        assertTrue(result is Failure.UnauthorizedFailure)
    }

    @Test
    fun `HttpException with non-401 and error body returns ApiFailure`() {
        // Given
        val responseBody = "Some error".toResponseBody()
        val response = Response.error<String>(500, responseBody)

        val exception = HttpException(response)

        // When
        val result = exception.toFailure()

        // Then
        assertTrue(result is Failure.ApiFailure)
        assertEquals(500, (result as Failure.ApiFailure).code)
    }

    @Test
    fun `HttpException with non-401 and error body is null returns ApiFailure`() {
        // Given
        val response = mockk<Response<Any>>()
        every { response.code() } returns 500
        every { response.message() } returns "Internal Server Error"
        every { response.errorBody() } returns null

        val exception = HttpException(response)

        // When
        val result = exception.toFailure()

        // Then
        assertTrue(result is Failure.ApiFailure)
        assertEquals(500, (result as Failure.ApiFailure).code)
        assertEquals("Internal Server Error", result.errorMessage)
    }

    @Test
    fun `IOException returns NetworkFailure`() {
        // Given
        val exception = IOException("Connection lost")

        // When
        val result = exception.toFailure()

        // Then
        assertTrue(result is Failure.NetworkFailure)
        assertEquals("Connection lost", (result as? Failure.NetworkFailure)?.errorMessage)
    }

    @Test
    fun `IOException with message is null returns NetworkFailure`() {
        // Given
        val exception = IOException()

        // When
        val result = exception.toFailure()

        // Then
        assertTrue(result is Failure.NetworkFailure)
        assertEquals("Network error", (result as? Failure.NetworkFailure)?.errorMessage)
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