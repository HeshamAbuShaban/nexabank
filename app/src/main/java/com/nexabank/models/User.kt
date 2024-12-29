package com.nexabank.models

import com.nexabank.models.enums.Role

data class User(
    val id: Long,
    val username: String,
    val email: String,
    val balance: Double,
    val role: Role
)