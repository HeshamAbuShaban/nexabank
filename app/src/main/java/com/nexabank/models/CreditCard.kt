package com.nexabank.models

import com.nexabank.database.enums.CardType

data class CreditCard(
    val id: Long,
    val cardNumber: String,
    val cvv: String,
    val expirationDate: String, // ISO 8601 format
    val isFrozen: Boolean,
    val balance: Double,
//    val creditLimit: Double,
    val cardType: CardType? = CardType.Visa,
//    val isDebit: Boolean,
)
