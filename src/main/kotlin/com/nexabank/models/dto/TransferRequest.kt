package com.nexabank.models.dto

data class TransferRequest(
    val recipientUsername: String,
    val amount: Double,
    val description: String?
)
