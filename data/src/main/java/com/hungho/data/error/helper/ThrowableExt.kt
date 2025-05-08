package com.hungho.data.error.helper

import com.google.gson.JsonParseException
import com.hungho.data.error.Failure
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toFailure(): Failure = when (this) {
    is HttpException -> {
        when (this.code()) {
            401 -> Failure.UnauthorizedFailure
            else -> {
                val errorBody = this.response()?.errorBody()?.string()
                Failure.ApiFailure(this.code(), errorBody ?: this.message())
            }
        }
    }

    is IOException -> Failure.NetworkFailure(message ?: "Network error")

    is JsonParseException, is JSONException -> Failure.ResponseParserFailure

    else -> Failure.UnknownFailure(message)
}