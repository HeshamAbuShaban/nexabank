package com.nexabank.models.core

data class RequestBodyForm(
    val username: String,
    val password: String,
    val email: String? = null
)