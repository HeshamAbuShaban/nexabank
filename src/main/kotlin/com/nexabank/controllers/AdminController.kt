package com.nexabank.controllers

import com.nexabank.models.Transaction
import com.nexabank.models.User
import com.nexabank.services.TransactionService
import com.nexabank.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "Admin", description = "APIs for handling Admins action")
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

    @Operation(
        summary = "Flag a transaction as an Admin",
        description = "Mark a specific transaction as unauthorized or suspicious."
    )
    @PutMapping("/transactions/{id}/flag")
    fun flagTransaction(@PathVariable id: Long): ResponseEntity<Transaction> {
        return ResponseEntity.ok(transactionService.flagTransaction(id))
    }

    @Operation(summary = "Get Some User's Details", description = "Fetch user's data (as an admin).")
    @GetMapping("/username")
    fun getUserByUsername(@RequestParam username: String): ResponseEntity<User> {
        val user = userService.findUserByUsername(username)
        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
    }

}
