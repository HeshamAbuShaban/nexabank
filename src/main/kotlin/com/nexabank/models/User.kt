package com.nexabank.models

import com.nexabank.models.enums.Role
import jakarta.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var balance: Double = 0.0,

    @Enumerated(EnumType.STRING)
    val role: Role = Role.USER
) {
    fun adjustBalance(amount: Double) {
        if (balance + amount < 0) {
            throw IllegalArgumentException("Insufficient balance")
        }
        balance += amount
        // Log the transaction for auditing
        println("Balance adjusted by $amount. New balance: $balance")
    }
}
