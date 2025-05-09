package com.hungho.data.helper

import android.util.Base64

internal object SecretHelper {
    init {
        System.loadLibrary("secret")
    }

    private external fun getEncodedDatabase(): String

    private external fun getGithubAccessToken(): String

    fun getDatabaseKey(): String {
        val encoded = getEncodedDatabase()
        val decodedBytes = Base64.decode(encoded, Base64.DEFAULT)
        return String(decodedBytes, charset("UTF-8"))
    }

    fun getAccessToken(): String {
        val encoded = getGithubAccessToken()
        val decodedBytes = Base64.decode(encoded, Base64.DEFAULT)
        return String(decodedBytes, charset("UTF-8"))
    }
}