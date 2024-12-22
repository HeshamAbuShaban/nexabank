package com.nexabank.models

import com.nexabank.models.enums.TransactionStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    val sender: User,

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    val recipient: User,

    @Column(nullable = false)
    val amount: Double,

    @Enumerated(EnumType.STRING)
    var status: TransactionStatus,

    val description: String? = null, // Optional description for the transaction

    /*
    * var isFlagged:Boolean = false
    * */

    @Column(nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now()
)

