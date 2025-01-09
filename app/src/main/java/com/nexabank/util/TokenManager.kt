package com.nexabank.util

import com.nexabank.core.AppSharedPreferences
import com.nexabank.core.NexaBankApp
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

object TokenManager {

    private val sharedPreferences: AppSharedPreferences by lazy {
        val entryPoint = EntryPointAccessors.fromApplication(
            NexaBankApp.instance.applicationContext, // Pass the application context
            AppSharedPreferencesEntryPoint::class.java
        )
        entryPoint.getAppSharedPreferences()
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface AppSharedPreferencesEntryPoint {
        fun getAppSharedPreferences(): AppSharedPreferences
    }

    fun saveToken(token: String) = sharedPreferences.saveToken(token)

    fun getToken(): String? = sharedPreferences.getToken()

    fun containsToken(): Boolean = sharedPreferences.containsToken()

    fun removeToken() = sharedPreferences.removeToken()

}
