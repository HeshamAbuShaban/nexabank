package com.nexabank.controllers

import com.nexabank.aop.security.ValidateAccess
import com.nexabank.models.dto.LoanRequest
import com.nexabank.models.dto.LoanReviewRequest
import com.nexabank.services.LoanService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "Loans", description = "APIs for handling loans")
@RestController
@RequestMapping("/api/loans")
class LoanController(private val loanService: LoanService) {

    @Operation(
        summary = "Request a loan",
        description = "Submit a loan request specifying the amount and repayment period."
    )
    @ValidateAccess(requireRole = ["ROLE_USER"], checkCurrentUser = true)
    @PostMapping("/{username}/request")
    fun requestLoan(
        @PathVariable username: String,
        @RequestBody loanRequest: LoanRequest
    ): ResponseEntity<String> {
        loanService.requestLoan(username, loanRequest)
        return ResponseEntity.ok("Loan request submitted and is under review.")
    }

    @Operation(
        summary = "Admin approves or rejects a loan",
        description = "Admins can approve or reject a loan request based on user eligibility."
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{loanId}/review")
    fun reviewLoan(
        @PathVariable loanId: Long,
        @RequestBody reviewRequest: LoanReviewRequest
    ): ResponseEntity<String> {
        loanService.reviewLoan(loanId, reviewRequest)
        return ResponseEntity.ok("Loan review processed successfully.")
    }

    @Operation(
        summary = "Repay a loan",
        description = "Deduct the repayment amount from the user's balance for the specified loan."
    )
    @ValidateAccess(requireRole = ["ROLE_USER"], checkCurrentUser = true)
    @PostMapping("/{username}/loans/{loanId}/repay")
    fun repayLoan(
        @PathVariable username: String,
        @PathVariable loanId: Long
    ): ResponseEntity<String> {
        loanService.repayLoan(username, loanId)
        return ResponseEntity.ok("Repayment processed successfully.")
    }

}
