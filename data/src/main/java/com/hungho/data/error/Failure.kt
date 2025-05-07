package com.hungho.data.error

sealed class Failure : Throwable() {
    class ApiFailure(val code: Int, val errorMessage: String) : Failure()

    class NetworkFailure(val errorMessage: String) : Failure()

    data object UnauthorizedFailure : Failure() {
        private fun readResolve(): Any = UnauthorizedFailure
    }

    class UnknownFailure(val errorMessage: String?) : Failure()

    object ResponseParserFailure : Failure() {
        private fun readResolve(): Any = ResponseParserFailure
    }
}