package com.nexabank.di

import android.content.Context
import android.content.SharedPreferences
import com.nexabank.core.AppSharedPreferences
import com.nexabank.core.Keys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences =
        context.getSharedPreferences(
            Keys.SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
}