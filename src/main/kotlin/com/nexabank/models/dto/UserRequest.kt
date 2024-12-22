package com.nexabank.models.dto

data class UserRequest(
    val username: String,
    val password: String,
    val email: String? = null,
    val balance: Double? = 0.0
)