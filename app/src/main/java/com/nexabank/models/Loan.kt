package com.nexabank.models

import com.nexabank.models.enums.LoanStatus
import java.time.LocalDateTime

data class Loan(
    val id: Long,
    val user: User,
    val amount: Double,
    var repaymentPeriod: Int, // in months
    var status: LoanStatus = LoanStatus.PENDING,
    val requestDate: String,
    var approvedDate: LocalDateTime? = null
)

