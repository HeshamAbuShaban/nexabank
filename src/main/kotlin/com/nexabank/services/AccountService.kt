package com.nexabank.services

import com.nexabank.models.Transaction
import com.nexabank.models.dto.TransferRequest
import com.nexabank.models.enums.TransactionStatus
import com.nexabank.repositories.TransactionRepository
import com.nexabank.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) {

    fun depositFunds(username: String, amount: Double) {
        val user = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("User not found")

        user.adjustBalance(amount)
        userRepository.save(user)
    }

    fun withdrawFunds(username: String, amount: Double) {
        val user = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("User not found")

        if (user.balance < amount) {
            throw IllegalArgumentException("Insufficient funds.")
        }

        user.adjustBalance(-amount)
        userRepository.save(user)
    }

    fun transferFunds(username: String, transferRequest: TransferRequest) {
        val sender = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("Sender not found")

        val recipient = userRepository.findByUsername(transferRequest.recipientUsername)
            ?: throw IllegalArgumentException("Recipient not found")

        if (sender.balance < transferRequest.amount) {
            throw IllegalArgumentException("Insufficient funds")
        }

        // Deduct and Add Balance

        sender.adjustBalance(-transferRequest.amount)
        recipient.adjustBalance(+transferRequest.amount)

        // Save Transaction
        val transaction = Transaction(
            sender = sender,
            recipient = recipient,
            amount = transferRequest.amount,
            status = TransactionStatus.COMPLETED,
            description = transferRequest.description,
        )
        transactionRepository.save(transaction)

        // Save Updated Users
        userRepository.save(sender)
        userRepository.save(recipient)
    }
}
