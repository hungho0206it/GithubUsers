package com.hungho.data.local.storage.helper

import android.util.Base64

object SecretHelper {
    init {
        System.loadLibrary("secret")
    }

    private external fun getEncodedDatabase(): String

    fun getDatabaseKey(): String {
        val encoded = getEncodedDatabase()
        val decodedBytes = Base64.decode(encoded, Base64.DEFAULT)
        return String(decodedBytes, charset("UTF-8"))
    }
}