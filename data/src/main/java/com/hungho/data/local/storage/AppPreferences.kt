package com.hungho.data.local.storage

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.hungho.data.helper.IEncrypted
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface IAppPreferences {
    suspend fun <T> saveValue(shareKey: AppPreferenceKey, value: T)
    fun <T> getValue(shareKey: AppPreferenceKey, default: T): T
    suspend fun saveValueEncrypted(shareKey: AppPreferenceKey, value: String)
    fun getValueEncrypted(shareKey: AppPreferenceKey): String?
}

class AppPreferences(
    context: Context,
    private val encryptedPrefsHelper: IEncrypted,
    private val coroutineDispatcher: CoroutineDispatcher,
) : IAppPreferences {

    private val sharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    override suspend fun <T> saveValue(shareKey: AppPreferenceKey, value: T) {
        withContext(coroutineDispatcher) {
            sharedPreferences.edit().apply {
                val key = shareKey.name
                when (value) {
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Boolean -> putBoolean(key, value)
                    is String -> putString(key, value)
                    is Float, is Double -> putFloat(key, value.toFloat())
                    is Set<*> -> putStringSet(key, value.convert())
                }
                apply()
            }
        }
    }

    override fun <T> getValue(shareKey: AppPreferenceKey, default: T): T {
        return try {
            sharedPreferences.let {
                val key = shareKey.name
                when (T::class) {
                    Int::class -> it.getInt(key, default as Int)
                    Long::class -> it.getLong(key, default as Long)
                    Boolean::class -> it.getBoolean(key, default as Boolean)
                    String::class -> it.getString(key, default as String)
                    Float::class, Double::class -> it.getFloat(key, default as Float)
                    Set::class -> it.getStringSet(key, null)
                    else -> default
                }
            }?.convert<T>() ?: default
        } catch (_: Exception) {
            default
        }
    }

    override suspend fun saveValueEncrypted(
        shareKey: AppPreferenceKey,
        value: String
    ) {
        withContext(coroutineDispatcher) {
            sharedPreferences.edit().apply {
                val key = shareKey.name
                val encrypted = encryptedPrefsHelper.encrypt(value)
                putString(key, encrypted)
                apply()
            }
        }
    }

    override fun getValueEncrypted(
        shareKey: AppPreferenceKey,
    ): String? {
        return sharedPreferences.let {
            val key = shareKey.name
            val encryptedValue = it.getString(key, null) ?: return null
            try {
                encryptedPrefsHelper.decrypt(encryptedValue)
            } catch (_: Exception) {
                return null
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    private fun <T> Any?.convert() = this as? T

    enum class AppPrefKey {
        LONG_LAST_TIME_FETCH_USER
    }
}

typealias AppPreferenceKey = AppPreferences.AppPrefKey