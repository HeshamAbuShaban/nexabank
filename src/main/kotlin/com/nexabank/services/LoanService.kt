package com.nexabank.services

import com.nexabank.aop.security.ValidateAccess
import com.nexabank.models.Loan
import com.nexabank.models.dto.LoanRequest
import com.nexabank.models.dto.LoanReviewRequest
import com.nexabank.models.enums.LoanStatus
import com.nexabank.repositories.LoanRepository
import com.nexabank.repositories.UserRepository
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class LoanService(
    private val userRepository: UserRepository,
    private val loanRepository: LoanRepository
) {

    fun requestLoan(username: String, loanRequest: LoanRequest) {
        val user = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("User not found")

        val loan = Loan(
            user = user,
            amount = loanRequest.amount,
            repaymentPeriod = loanRequest.repaymentPeriod,
        )

        loanRepository.save(loan)
    }

    @Transactional
    fun reviewLoan(loanId: Long, reviewRequest: LoanReviewRequest) {
        val loan = loanRepository.findById(loanId)
            .orElseThrow { IllegalArgumentException("Loan not found") }

        if (reviewRequest.approved) {
            loan.status = LoanStatus.APPROVED
            loan.approvedDate = LocalDateTime.now()

            val user = loan.user
            user.adjustBalance(loan.amount)
            userRepository.save(user)
        } else {
            loan.status = LoanStatus.REJECTED
        }

        loanRepository.save(loan)
    }

    fun repayLoan(username: String, loanId: Long) {
        val loan = loanRepository.findById(loanId)
            .orElseThrow { IllegalArgumentException("Loan not found") }

        if (loan.user.username != username) {
            throw AccessDeniedException("You are not authorized to repay this loan.")
        }

        if (loan.status != LoanStatus.APPROVED) {
            throw IllegalArgumentException("Only approved loans can be repaid.")
        }

        val monthlyPayment = loan.amount / loan.repaymentPeriod
        if (loan.user.balance < monthlyPayment) {
            throw IllegalArgumentException("Insufficient funds for repayment.")
        }

        // Deduct monthly payment
        loan.user.adjustBalance(-monthlyPayment)
        userRepository.save(loan.user)

        // Update loan repayment period
        loan.repaymentPeriod -= 1
        if (loan.repaymentPeriod <= 0) {
            loan.status = LoanStatus.COMPLETED
        }
        loanRepository.save(loan)
    }

}
