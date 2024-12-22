package com.nexabank.controllers

import com.nexabank.models.User
import com.nexabank.models.dto.UserRequest
import com.nexabank.security.JwtUtil
import com.nexabank.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/auth")
class AuthController
    (
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder
) {

    // Registration endpoint
    @PostMapping("/register")
    fun register(@RequestBody registerRequest: UserRequest): ResponseEntity<String> {
        // Hash the password before saving it
        val encodedPassword = passwordEncoder.encode(registerRequest.password)
        // Create a User object based on the incoming data
        val newUser = User(
            username = registerRequest.username,
            password = encodedPassword, // You should hash the password in production
            email = registerRequest.email!!,
            balance = registerRequest.balance!!
        )

        // Save user to database
        userService.saveUser(newUser)

        return ResponseEntity.ok("User registered successfully")
    }

    @PostMapping("/login")
    fun login(@RequestBody userRequest: UserRequest): String {
        return try {
            println("Login attempt: username=${userRequest.username}")
            val auth = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(userRequest.username, userRequest.password)
            )
            println("Authentication successful for: ${auth.name}")
            JwtUtil.generateToken(auth.name)
        } catch (ex: Exception) {
            println("Authentication failed: ${ex.message}")
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed")
        }
    }

}