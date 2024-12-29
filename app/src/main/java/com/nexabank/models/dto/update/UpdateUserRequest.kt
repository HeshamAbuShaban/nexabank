package com.nexabank.models.dto.update

import com.nexabank.models.enums.Role


data class UpdateUserRequest(
    val username: String?,
    val password: String?,
    val email: String?,
    val role: Role?
)