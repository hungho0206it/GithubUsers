package com.hungho.data.local.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import com.hungho.data.helper.EncryptedPrefsHelper
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkObject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AppPreferencesTest {
    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var encryptedPrefsHelper: EncryptedPrefsHelper
    private lateinit var appPreferences: AppPreferences

    private val testKey = AppPreferences.AppPrefKey.LONG_LAST_TIME_FETCH_USER

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
        encryptedPrefsHelper = mockk()
        appPreferences = AppPreferences(context, encryptedPrefsHelper, UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        unmockkObject(sharedPreferences)
    }

    @Test
    fun `Save and get non-encrypted Int value`() = runTest {
        val value = 42
        appPreferences.saveValue(testKey, value)
        val result = appPreferences.getValue(testKey, value)
        assertEquals(value, result)
    }

    @Test
    fun `Save and get non-encrypted Long value`() = runTest {
        val value = 123L
        appPreferences.saveValue(testKey, value)
        val result = appPreferences.getValue(testKey, value)
        assertEquals(value, result)
    }

    @Test
    fun `Save and get non-encrypted Double value`() = runTest {
        val value = 12.3
        appPreferences.saveValue(testKey, value)
        val result = appPreferences.getValue(testKey, value)
        assertEquals(value, result)
    }

    @Test
    fun `Save and get non-encrypted Float value`() = runTest {
        val value = 12.3f
        appPreferences.saveValue(testKey, value)
        val result = appPreferences.getValue(testKey, value)
        assertEquals(value, result)
    }

    @Test
    fun `Save and get non-encrypted Boolean value`() = runTest {
        val value = true
        appPreferences.saveValue(testKey, value)
        val result = appPreferences.getValue(testKey, value)
        assertEquals(value, result)
    }

    @Test
    fun `Save and get non-encrypted String value`() = runTest {
        val value = "hello"
        appPreferences.saveValue(testKey, value)
        val result = appPreferences.getValue(testKey, value)
        assertEquals(value, result)
    }

    @Test
    fun `Save and get non-encrypted StringSet value`() = runTest {
        val value = setOf("a", "b")
        appPreferences.saveValue(testKey, value)
        val result = appPreferences.getValue(testKey, value)
        assertEquals(value, result)
    }

    @Test
    fun `Save and get encrypted String value`() = runTest {
        val original = "secret"
        val encoded = "encoded"
        every { encryptedPrefsHelper.encrypt(original) } returns encoded
        every { encryptedPrefsHelper.decrypt(encoded) } returns original

        appPreferences.saveValueEncrypted(testKey, original)
        val result = appPreferences.getValueEncrypted(testKey)
        assertEquals(original, result)
    }

    @Test
    fun `Get encrypted value with corrupted cipher returns default`() {
        val encrypted = "encrypted"
        every { encryptedPrefsHelper.decrypt(encrypted) } throws IllegalArgumentException()

        val result = appPreferences.getValueEncrypted(testKey)
        assertNull(result)
    }

    @Test
    fun `Get non-encrypted value with missing key returns default`() {
        val result = appPreferences.getValue(testKey, "default")
        assertEquals("default", result)
    }

    @Test
    fun `Catch exception during SharedPreferences read returns default`() {
        val result = appPreferences.getValue(testKey, "default")
        assertEquals("default", result)
    }
}