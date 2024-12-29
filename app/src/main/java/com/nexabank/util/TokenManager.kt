package com.nexabank.util

import com.nexabank.core.AppSharedPreferences

object TokenManager {

    private val sharedPreferences = AppSharedPreferences

    fun saveToken(token: String) = sharedPreferences.saveToken(token)

    fun getToken(): String? = sharedPreferences.getToken()

    fun containsToken(): Boolean = sharedPreferences.containsToken()

    fun removeToken() = sharedPreferences.removeToken()

}
