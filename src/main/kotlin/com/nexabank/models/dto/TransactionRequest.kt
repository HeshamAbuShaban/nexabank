package com.nexabank.models.dto

data class TransactionRequest(
    val senderUsername: String,
    val recipientUsername: String,
    val amount: Double,
    val description: String? = null,
    val category: String
)
