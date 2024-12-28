package com.nexabank.models

import com.nexabank.models.enums.LoanStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Loan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    val user: User,

    val amount: Double,

    var repaymentPeriod: Int, // in months

    @Enumerated(EnumType.STRING)
    var status: LoanStatus = LoanStatus.PENDING,

    val requestDate: LocalDateTime = LocalDateTime.now(),

    var approvedDate: LocalDateTime? = null
)

