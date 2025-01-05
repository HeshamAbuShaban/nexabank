package com.nexabank.models

import com.nexabank.models.enums.TransactionStatus
import com.nexabank.models.enums.TransactionType

data class Transaction(
    val id: Long,
    val sender: User,
    val recipient: User,
    val amount: Double,
    val description: String?,
    val status: TransactionStatus,
    val date: String?,
    val type: TransactionType?,
)