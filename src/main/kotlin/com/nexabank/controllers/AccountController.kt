package com.nexabank.controllers

import com.nexabank.aop.security.ValidateAccess
import com.nexabank.models.dto.TransferRequest
import com.nexabank.models.dto.DepositRequest
import com.nexabank.models.dto.WithdrawRequest
import com.nexabank.services.AccountService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Accounts", description = "APIs for handling accounts")
@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @Operation(
        summary = "Deposit funds",
        description = "Deposit money into the user's account."
    )
    @ValidateAccess(requireRole = "ROLE_USER", checkCurrentUser = true)
    @PostMapping("/{username}/deposit")
    fun depositFunds(
        @PathVariable username: String,
        @RequestBody depositRequest: DepositRequest
    ): ResponseEntity<String> {
        accountService.depositFunds(username, depositRequest.amount)
        return ResponseEntity.ok("Deposit successful.")
    }

    @Operation(
        summary = "Withdraw funds",
        description = "Withdraw money from the user's account."
    )
    @ValidateAccess(requireRole = "ROLE_USER", checkCurrentUser = true)
    @PostMapping("/{username}/withdraw")
    fun withdrawFunds(
        @PathVariable username: String,
        @RequestBody withdrawRequest: WithdrawRequest
    ): ResponseEntity<String> {
        accountService.withdrawFunds(username, withdrawRequest.amount)
        return ResponseEntity.ok("Withdrawal successful.")
    }

    @Operation(
        summary = "Transfer funds",
        description = "Transfer funds between your account and another user."
    )
    @ValidateAccess(requireRole = "ROLE_USER", checkCurrentUser = true)
    @PostMapping("/{username}/transfer")
    fun transferFunds(
        @PathVariable username: String,
        @RequestBody transferRequest: TransferRequest
    ): ResponseEntity<String> {
        accountService.transferFunds(username, transferRequest)
        return ResponseEntity.ok("Transfer successful.")
    }
}
