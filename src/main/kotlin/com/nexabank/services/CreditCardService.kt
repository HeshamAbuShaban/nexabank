package com.nexabank.services

import com.nexabank.models.CreditCard
import com.nexabank.repositories.CreditCardRepository
import com.nexabank.repositories.UserRepository
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CreditCardService(private val cardRepository: CreditCardRepository, private val userRepository: UserRepository) {

    fun generateCard(username: String) {
        val user = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("User not found")

        val card = CreditCard(
            cardNumber = generateCardNumber(),
            cvv = generateCvv(),
            expirationDate = LocalDateTime.now().plusYears(3),
            user = user
        )
        cardRepository.save(card)
    }

    fun toggleFreeze(username: String, cardId: Long) {
        val card = cardRepository.findById(cardId)
            .orElseThrow { IllegalArgumentException("Card not found") }

        if (card.user.username != username) {
            throw AccessDeniedException("Unauthorized action.")
        }

        card.isFrozen = !card.isFrozen
        cardRepository.save(card)
    }

    private fun generateCardNumber(): String {
        return (1000000000000000..9999999999999999).random().toString()
    }

    private fun generateCvv(): String {
        return (100..999).random().toString()
    }
}
