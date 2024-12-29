package com.nexabank.models.responses

import com.nexabank.models.Transaction

data class TransactionResponse(
    val content: List<Transaction>,
    val totalPages: Int,
    val totalElements: Int
)
