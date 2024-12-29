package com.nexabank.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nexabank.database.dao.CreditCardDao
import com.nexabank.database.entities.CreditCardEntity

@Database(
    entities = [CreditCardEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NexaBankDatabase : RoomDatabase() {
    abstract fun creditCardDao(): CreditCardDao
}