package com.nexabank.models.dto

data class SpendingInsights(
    val spendingByCategory: Map<String?, Double>
)
