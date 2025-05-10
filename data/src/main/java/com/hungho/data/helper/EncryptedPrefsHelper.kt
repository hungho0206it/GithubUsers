package com.hungho.data.helper

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

interface KeyProvider {
    fun getSecretKey(): SecretKey
}

interface IEncrypted {
    fun encrypt(value: String): String
    fun decrypt(encryptedBase64: String): String
}

class AndroidKeyStoreProvider : KeyProvider {
    override fun getSecretKey(): SecretKey {
        val keyGen =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val spec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        keyGen.init(spec)
        return keyGen.generateKey()
    }

    companion object {
        private const val KEY_ALIAS = "APP_PREF_KEY"
    }

}

class EncryptedPrefsHelper(private val keyProvider: KeyProvider) : IEncrypted {
    override fun encrypt(value: String): String {
        val cipher = Cipher.getInstance(CRYPTO_TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, keyProvider.getSecretKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(value.toByteArray(Charsets.UTF_8))
        val combined = iv + encrypted
        return Base64.encodeToString(combined, Base64.DEFAULT)
    }

    override fun decrypt(encryptedBase64: String): String {
        val combined = Base64.decode(encryptedBase64, Base64.DEFAULT)
        val iv = combined.copyOfRange(0, 12)
        val encrypted = combined.copyOfRange(12, combined.size)
        val cipher = Cipher.getInstance(CRYPTO_TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, keyProvider.getSecretKey(), GCMParameterSpec(128, iv))
        val decrypted = cipher.doFinal(encrypted)
        return String(decrypted, Charsets.UTF_8)
    }

    companion object {
        private const val CRYPTO_TRANSFORMATION = "AES/GCM/NoPadding"
    }
}