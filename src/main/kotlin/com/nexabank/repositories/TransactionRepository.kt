package com.nexabank.repositories

import com.nexabank.models.Transaction
import com.nexabank.models.enums.TransactionStatus
import com.nexabank.models.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
    override fun findAll(pageable: Pageable): Page<Transaction>
    fun findBySender(sender: User): List<Transaction>
    fun findByRecipient(recipient: User): List<Transaction>
    fun findByStatus(status: TransactionStatus): List<Transaction>
    fun findBySenderOrRecipient(sender: User, recipient: User, pageable: Pageable): Page<Transaction>
    fun findBySenderOrRecipientAndStatus(sender: User, recipient: User, status: TransactionStatus, pageable: Pageable): Page<Transaction>
}
