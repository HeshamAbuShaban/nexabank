package com.nexabank.models.dto

data class LoanRequest(
    val amount: Double,
    val repaymentPeriod: Int // in months
)
