package com.hungho.data.error

sealed class Failure : Throwable() {
    class ApiFailure(val code: Int, val errorMessage: String) : Failure()

    class NetworkFailure(val errorMessage: String) : Failure()

    data object UnauthorizedFailure : Failure()

    class UnknownFailure(val errorMessage: String?) : Failure()

    object ResponseParserFailure : Failure()
}