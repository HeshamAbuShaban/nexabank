package com.nexabank.di

import android.content.Context
import androidx.room.Room
import com.nexabank.core.Keys
import com.nexabank.database.NexaBankDatabase
import com.nexabank.database.dao.CreditCardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context,
    ) = Room.databaseBuilder(
        context = applicationContext,
        klass = NexaBankDatabase::class.java,
        name = Keys.DATABASE_NAME
    ).fallbackToDestructiveMigrationFrom()
        .build()

    @Provides
    @Singleton
    fun provideCreditCardDao(database: NexaBankDatabase): CreditCardDao = database.creditCardDao()
}
