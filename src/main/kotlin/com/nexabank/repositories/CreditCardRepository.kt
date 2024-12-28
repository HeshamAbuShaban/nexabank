package com.nexabank.repositories

import com.nexabank.models.CreditCard
import org.springframework.data.jpa.repository.JpaRepository

interface CreditCardRepository : JpaRepository<CreditCard, Long>
