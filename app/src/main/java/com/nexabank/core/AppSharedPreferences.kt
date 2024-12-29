package com.nexabank.core

import android.content.SharedPreferences
import com.nexabank.core.Keys.TOKEN_PREF
import javax.inject.Inject

/**
 * This class is used to save and retrieve data from the SharedPreferences.
 * it gather the common methods to save and retrieve data from the SharedPreferences.
 * so we could call it from anywhere in the app.
 * its a controller class to the SharedPreferences class.
 * */
object AppSharedPreferences {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    // Token
    fun saveToken(token: String) {
        editor
            .putString(TOKEN_PREF, token)
            .apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_PREF, null)
    }

    fun containsToken(): Boolean {
        return sharedPreferences.contains(TOKEN_PREF)
    }

    fun removeToken() {
        editor.apply {
            remove(TOKEN_PREF)
            apply()
        }
    }

    // SharedPreferences Utils

    fun clearSharedPreferences() {
        editor.clear().apply()
    }
    fun getSharedPreferencesString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun getSharedPreferencesInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun putSharedPreferencesString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun putSharedPreferencesInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    fun putSharedPreferencesBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    fun getSharedPreferencesBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
}