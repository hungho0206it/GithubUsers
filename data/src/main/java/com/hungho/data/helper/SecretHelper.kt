package com.hungho.data.helper

import android.util.Base64

internal interface ISecretHelper {
    fun getDatabaseKey(): String
    fun getAccessToken(): String
}

internal class SecretHelper : ISecretHelper {
    init {
        System.loadLibrary("secret")
    }

    private external fun getEncodedDatabase(): String

    private external fun getGithubAccessToken(): String

    // Get database key from native code and decode from Base64 to string
    override fun getDatabaseKey(): String {
        val encoded = getEncodedDatabase()
        val decodedBytes = Base64.decode(encoded, Base64.DEFAULT)
        return String(decodedBytes, charset("UTF-8"))
    }

    // Get github access token from native code and decode from Base64 to string
    override fun getAccessToken(): String {
        val encoded = getGithubAccessToken()
        val decodedBytes = Base64.decode(encoded, Base64.DEFAULT)
        return String(decodedBytes, charset("UTF-8"))
    }
}