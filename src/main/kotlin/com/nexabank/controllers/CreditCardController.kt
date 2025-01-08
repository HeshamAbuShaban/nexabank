package com.nexabank.controllers

import com.nexabank.aop.security.ValidateAccess
import com.nexabank.services.CreditCardService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "CreditCards", description = "APIs for handling CreditCards")
@RestController
@RequestMapping("/api/cards")
class CreditCardController(private val cardService: CreditCardService) {

    @Operation(
        summary = "Generate a virtual card",
        description = "Create a virtual credit card for secure online transactions."
    )
    @ValidateAccess(requireRole = ["ROLE_USER"], checkCurrentUser = true)
    @PostMapping("/{username}/generate")
    fun generateCard(@PathVariable username: String): ResponseEntity<String> {
        cardService.generateCard(username)
        return ResponseEntity.ok("Card generated successfully.")
    }

    @Operation(
        summary = "Freeze or unfreeze a card",
        description = "Toggle the freeze status of a credit card."
    )
    @ValidateAccess(requireRole = ["ROLE_USER"], checkCurrentUser = true)
    @PutMapping("/{username}/{cardId}/toggle")
    fun toggleCardFreeze(@PathVariable username: String, @PathVariable cardId: Long): ResponseEntity<String> {
        cardService.toggleFreeze(username, cardId)
        return ResponseEntity.ok("Card freeze status updated.")
    }
}
