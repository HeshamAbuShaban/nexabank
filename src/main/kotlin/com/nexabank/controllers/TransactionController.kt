package com.nexabank.controllers

import com.nexabank.models.Transaction
import com.nexabank.models.dto.TransactionRequest
import com.nexabank.services.TransactionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping("/create")
    fun createTransaction(@RequestBody transactionRequest: TransactionRequest): ResponseEntity<Transaction> {
        val transaction = transactionService.createTransaction(
            transactionRequest.senderUsername,
            transactionRequest.recipientUsername,
            transactionRequest.amount,
            transactionRequest.description
        )
        return ResponseEntity.ok(transaction)
    }

    @PostMapping("/complete/{transactionId}")
    fun completeTransaction(@PathVariable transactionId: Long): ResponseEntity<Transaction> {
        val transaction = transactionService.completeTransaction(transactionId)
        return ResponseEntity.ok(transaction)
    }

    @GetMapping("/history/{username}")
    fun getTransactionHistory(@PathVariable username: String): ResponseEntity<List<Transaction>> {
        val transactions = transactionService.getTransactionHistory(username)
        return ResponseEntity.ok(transactions)
    }

    @GetMapping("/reverse/{transactionId}")
    fun reverseTransaction(@PathVariable transactionId: Long): ResponseEntity<Transaction> {
        val transactions = transactionService.reverseTransaction(transactionId)
        return ResponseEntity.ok(transactions)
    }
}
