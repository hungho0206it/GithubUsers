package com.hungho.data.helper

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

@RunWith(RobolectricTestRunner::class)
class EncryptedPrefsHelperTest {
    @Test
    fun `encrypt and decrypt returns original value`() {
        // Given
        val secretKey = generateAesKey()
        val keyProvider = mockk<KeyProvider>()
        every { keyProvider.getSecretKey() } returns secretKey

        val helper = EncryptedPrefsHelper(keyProvider)

        val originalValue = "Hello, World!"

        // When
        val encryptedValue = helper.encrypt(originalValue)
        val decryptedValue = helper.decrypt(encryptedValue)

        //Then
        assertEquals(originalValue, decryptedValue)
    }

    private fun generateAesKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        return keyGen.generateKey()
    }
}