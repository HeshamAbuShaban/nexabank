package com.nexabank.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nexabank.database.enums.CardType

@Entity(tableName = "credit_cards")
data class CreditCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val cardNumber: String,
    val cvv: String,
    val expirationDate: String,
    var isFrozen: Boolean,
    val cardType: CardType
)
