package com.hungho.data.local.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hungho.data.helper.EncryptedPrefsHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Context.getAppPreferences(): SharedPreferences {
    return getSharedPreferences(packageName, Context.MODE_PRIVATE)
}

@Suppress("UNCHECKED_CAST")
fun <T> Any.convert() = this as T

class AppPreferences(val context: Context) {
    fun <T> saveValueEncrypted(shareKey: AppPreferenceKey, value: T) {
        CoroutineScope(Dispatchers.IO).launch {
            context.getAppPreferences().edit().apply {
                val key = shareKey.name
                val stringValue = when (value) {
                    is Int, is Long, is Boolean, is Float, is String -> value.toString()
                    is Set<*> -> Gson().toJson(value)
                    else -> Gson().toJson(value)
                }
                val encrypted = EncryptedPrefsHelper.encrypt(stringValue)
                putString(key, encrypted)
                apply()
            }
        }
    }

    inline fun <reified T> getValueEncrypted(shareKey: AppPreferenceKey, default: T): T {
        return try {
            context.getAppPreferences().let {
                val key = shareKey.name
                val encryptedValue = it.getString(key, null) ?: return default
                val decrypted = try {
                    EncryptedPrefsHelper.decrypt(encryptedValue)
                } catch (_: Exception) {
                    return default
                }

                when (T::class) {
                    Int::class -> decrypted.toIntOrNull() ?: default
                    Long::class -> decrypted.toLongOrNull() ?: default
                    Boolean::class -> decrypted.toBooleanStrictOrNull() ?: default
                    Float::class -> decrypted.toFloatOrNull() ?: default
                    String::class -> decrypted as T
                    Set::class -> Gson().fromJson(
                        decrypted,
                        object : TypeToken<Set<String>>() {}.type
                    )

                    else -> Gson().fromJson(decrypted, T::class.java)
                }
            }?.convert<T>() ?: default
        } catch (_: Exception) {
            default
        }
    }

    fun <T> saveValue(shareKey: AppPreferenceKey, value: T) {
        CoroutineScope(Dispatchers.IO).launch {
            context.getAppPreferences().edit().apply {
                val key = shareKey.name
                when (value) {
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Boolean -> putBoolean(key, value)
                    is String -> putString(key, value)
                    is Float -> putFloat(key, value)
                    is Set<*> -> putStringSet(key, value.convert())
                }
                apply()
            }
        }
    }

    inline fun <reified T> getValue(shareKey: AppPreferenceKey, default: T): T {
        return try {
            context.getAppPreferences().let {
                val key = shareKey.name
                when (T::class) {
                    Int::class -> it.getInt(key, default as Int)
                    Long::class -> it.getLong(key, default as Long)
                    Boolean::class -> it.getBoolean(key, default as Boolean)
                    String::class -> it.getString(key, default as String)
                    Float::class -> it.getFloat(key, default as Float)
                    Set::class -> it.getStringSet(key, null)
                    else -> default
                }
            }?.convert<T>() ?: default
        } catch (_: Exception) {
            default
        }
    }

    enum class AppPrefKey {
        LONG_LAST_TIME_FETCH_USER
    }
}

typealias AppPreferenceKey = AppPreferences.AppPrefKey