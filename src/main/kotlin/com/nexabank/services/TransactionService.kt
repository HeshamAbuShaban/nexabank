package com.nexabank.services

import com.nexabank.models.Transaction
import com.nexabank.models.enums.TransactionStatus
import com.nexabank.repositories.TransactionRepository
import com.nexabank.repositories.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository, // To get users for transactions
    private val notificationService: NotificationService
) {

    fun createTransaction(senderUsername: String, recipientUsername: String, amount: Double, description: String?): Transaction {
        val sender = userRepository.findByUsername(senderUsername)
            ?: throw IllegalArgumentException("Sender not found")
        val recipient = userRepository.findByUsername(recipientUsername)
            ?: throw IllegalArgumentException("Recipient not found")

        // You could add some validation logic here, e.g., check if the sender has enough balance
        if (sender.balance < amount) {
            throw IllegalArgumentException("Sender has insufficient balance")
        }

        val transaction = Transaction(
            sender = sender,
            recipient = recipient,
            amount = amount,
            status = TransactionStatus.PENDING,
            description = description
        )

        // Deduct from sender and add to recipient (you can also validate balances here)
        sender.balance -= amount
        recipient.balance += amount

        // Save both the transaction and updated user data
        userRepository.save(sender)
        userRepository.save(recipient)

        // Notify sender and recipient
        notificationService.sendTransactionNotification(sender.email, "Transaction Successful", "Your transaction was successful.")
        notificationService.sendTransactionNotification(recipient.email, "You Received Money", "You received $$amount from ${sender.username}.")

        return transactionRepository.save(transaction)
    }

    fun completeTransaction(transactionId: Long): Transaction {
        val transaction = transactionRepository.findById(transactionId)
            .orElseThrow { IllegalArgumentException("Transaction not found") }

        if (transaction.status == TransactionStatus.PENDING) {
            transaction.status = TransactionStatus.COMPLETED
            return transactionRepository.save(transaction)
        } else {
            throw IllegalArgumentException("Transaction is already completed or failed")
        }
    }

    fun getTransactionHistory(username: String): List<Transaction> {
        val user = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("User not found")

        // Get all transactions for the user (both sent and received)
        return transactionRepository.findBySender(user) + transactionRepository.findByRecipient(user)
    }

    fun getTransactionHistory(username: String, page: Int, size: Int, status: TransactionStatus?): Page<Transaction> {
        val user = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("User not found")

        val pageable: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"))

        return if (status != null) {
            transactionRepository.findBySenderOrRecipientAndStatus(user, user, status, pageable)
        } else {
            transactionRepository.findBySenderOrRecipient(user, user, pageable)
        }
    }


    fun reverseTransaction(transactionId: Long): Transaction {
        val transaction = transactionRepository.findById(transactionId)
            .orElseThrow { IllegalArgumentException("Transaction not found") }

        if (transaction.status != TransactionStatus.COMPLETED) {
            throw IllegalArgumentException("Only completed transactions can be reversed")
        }

        val sender = transaction.sender
        val recipient = transaction.recipient

        // Reverse amounts
        sender.balance += transaction.amount
        recipient.balance -= transaction.amount

        transaction.status = TransactionStatus.FAILED

        userRepository.save(sender)
        userRepository.save(recipient)

        // Notify both parties about the reversal
        notificationService.sendTransactionNotification(
            sender.email,
            "Transaction Reversed",
            "A transaction of $${transaction.amount} was reversed. Your balance has been updated."
        )
        notificationService.sendTransactionNotification(
            recipient.email,
            "Transaction Reversed",
            "A transaction of $${transaction.amount} was reversed. Your balance has been updated."
        )

        return transactionRepository.save(transaction)
    }

    fun getAllTransactions(page: Int, size: Int): Page<Transaction> {
        val pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"))
        return transactionRepository.findAll(pageable)
    }

    // TODO("Later on")
    /*fun flagTransaction(transactionId: Long): Transaction {
        val transaction = transactionRepository.findById(transactionId)
            .orElseThrow { IllegalArgumentException("Transaction not found") }

        transaction.isFlagged = true // Add a `flagged` field to your Transaction entity.
        return transactionRepository.save(transaction)
    }*/


}
