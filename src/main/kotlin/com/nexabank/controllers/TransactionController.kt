package com.nexabank.controllers

import com.nexabank.aop.security.ValidateAccess
import com.nexabank.models.Transaction
import com.nexabank.models.dto.SpendingInsights
import com.nexabank.models.dto.TransactionRequest
import com.nexabank.models.enums.TransactionStatus
import com.nexabank.services.TransactionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Transactions", description = "APIs for handling transactions")
@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val transactionService: TransactionService
) {

    @Operation(
        summary = "Create transaction",
        description = "Execute a transaction using your token and transfer funds to a legitimate existing user."
    )
    @PostMapping("/create")
    fun createTransaction(@RequestBody transactionRequest: TransactionRequest): ResponseEntity<Transaction> {
        val transaction = transactionService.createTransaction(
            transactionRequest.senderUsername,
            transactionRequest.recipientUsername,
            transactionRequest.amount,
            transactionRequest.description,
            transactionRequest.category
        )
        return ResponseEntity.ok(transaction)
    }

    @Operation(summary = "Get complete transaction by Id", description = "Fetch transaction by it's Id.")
    @PostMapping("/complete/{transactionId}")
    fun completeTransaction(@PathVariable transactionId: Long): ResponseEntity<Transaction> {
        val transaction = transactionService.completeTransaction(transactionId)
        return ResponseEntity.ok(transaction)
    }

    @Operation(
        summary = "Get spending insights",
        description = "Fetch categorized spending insights based on transaction history."
    )
    @GetMapping("/{username}/spending-insights")
    @ValidateAccess(requireRole = ["ROLE_USER"], checkCurrentUser = true)
    fun getSpendingInsights(@PathVariable username: String): ResponseEntity<SpendingInsights> {
        return ResponseEntity.ok(transactionService.getSpendingInsights(username))
    }

    @Operation(
        summary = "Flag a transaction",
        description = "Mark a specific transaction as unauthorized or suspicious."
    )
    @PutMapping("/{transactionId}/flag")
    fun flagTransaction(@PathVariable transactionId: Long): ResponseEntity<String> {
        return ResponseEntity.ok("Transaction flagged for review.\n" + transactionService.flagTransaction(transactionId))
    }

    @Operation(
        summary = "Get Pageable Transaction History",
        description = "-Pageable- Get all Transaction for any user."
    )
    @GetMapping("/history/{username}")
    fun getTransactionHistory(
        @PathVariable username: String,
        @RequestParam page: Int,
        @RequestParam size: Int,
        @RequestParam status: TransactionStatus?
    ): ResponseEntity<Iterable<Transaction>> {
        val transactions = transactionService.getTransactionHistory(username, page, size, status)
        return ResponseEntity.ok(transactions)
    }

    @Operation(summary = "Reverse A Completed Transaction", description = "Revert a completed successful transaction.")
    @GetMapping("/reverse/{transactionId}")
    fun reverseTransaction(@PathVariable transactionId: Long): ResponseEntity<Transaction> {
        val transactions = transactionService.reverseTransaction(transactionId)
        return ResponseEntity.ok(transactions)
    }
}
