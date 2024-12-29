package com.nexabank.models

import com.nexabank.models.enums.TransactionStatus

data class Transaction(
    val id: Long,
    val sender: User,
    val recipient: User,
    val amount: Double,
    val description: String?,
    val status: TransactionStatus
)