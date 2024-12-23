package com.nexabank.controllers

import com.nexabank.models.Transaction
import com.nexabank.models.User
import com.nexabank.services.TransactionService
import com.nexabank.services.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
class AdminController(
    private val userService: UserService,
    private val transactionService: TransactionService
) {

    @GetMapping("/transactions")
    fun getAllTransactions(
        @RequestParam page: Int,
        @RequestParam size: Int
    ): Page<Transaction> {
        return transactionService.getAllTransactions(page, size)
    }

    /*@PutMapping("/transactions/{id}/flag")
    fun flagTransaction(@PathVariable id: Long): ResponseEntity<Transaction> {
        val transaction = transactionService.flagTransaction(id)
        return ResponseEntity.ok(transaction)
    }*/

    @Operation(summary = "Get Some User's Details", description = "Fetch user's data (as an admin).")
    @GetMapping("/username")
    fun getUserByUsername(@RequestParam username: String): ResponseEntity<User> {
        val user = userService.findUserByUsername(username)
        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
    }

}
