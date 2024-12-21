package com.nexabank.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

object JwtUtil {
    private const val EXPIRATION_TIME: Long = 86400000 // 24 hours
    private val SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256)


    // Generate JWT token
    fun generateToken(username: String): String {
        val claims: Map<String, Any> = HashMap()
        return createToken(claims, username)
    }

    // Extract username from JWT token
    fun extractUsername(token: String): String? {
        return extractClaim(token, Claims::getSubject)
    }

    // Validate JWT token
    fun validateToken(token: String, userDetails: org.springframework.security.core.userdetails.UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    // Check if the token has expired
    private fun isTokenExpired(token: String): Boolean {
        val expiration = extractClaim(token, Claims::getExpiration)
        return expiration.before(Date())
    }

    // Extract claims from the token
    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver.invoke(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .body
    }

    // Create the JWT token
    private fun createToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SECRET_KEY)
            .compact()
    }


    fun isValidToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUsernameFromToken(token: String): String {
        val claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).body
        return claims.subject
    }
}