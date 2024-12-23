package com.nexabank.models.dto

import com.nexabank.models.enums.Role

data class UserRequest(
    val username: String,
    val password: String,
    val email: String? = null,
    val balance: Double? = 0.0,
    val role:Role = Role.USER
)