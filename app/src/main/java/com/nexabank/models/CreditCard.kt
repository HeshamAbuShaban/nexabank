package com.nexabank.models

data class CreditCard(
    val id: Long,
    val cardNumber: String,
    val cvv: String,
    val expirationDate: String, // ISO 8601 format
    val isFrozen: Boolean
)
