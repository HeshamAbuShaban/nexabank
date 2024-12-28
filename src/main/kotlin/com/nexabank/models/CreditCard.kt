package com.nexabank.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class CreditCard(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val cardNumber: String,

    val cvv: String,

    val expirationDate: LocalDateTime,

    var isFrozen: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Explicit foreign key column
    val user: User
)
