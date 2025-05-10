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

    override fun getDatabaseKey(): String {
        val encoded = getEncodedDatabase()
        val decodedBytes = Base64.decode(encoded, Base64.DEFAULT)
        return String(decodedBytes, charset("UTF-8"))
    }

    override fun getAccessToken(): String {
        val encoded = getGithubAccessToken()
        val decodedBytes = Base64.decode(encoded, Base64.DEFAULT)
        return String(decodedBytes, charset("UTF-8"))
    }
}