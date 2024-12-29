package com.nexabank.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credit_cards")
data class CreditCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val cardNumber: String,
    val cvv: String,
    val expirationDate: String,
    var isFrozen: Boolean
)
