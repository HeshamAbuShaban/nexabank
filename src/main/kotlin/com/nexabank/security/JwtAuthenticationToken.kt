package com.nexabank.security

import org.springframework.security.authentication.AbstractAuthenticationToken

class JwtAuthenticationToken(
    private val username: String
) : AbstractAuthenticationToken(null) {

    override fun getCredentials(): Any? = null
    override fun getPrincipal(): Any = username
}
