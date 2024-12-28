package com.nexabank.repositories

import com.nexabank.models.Loan
import org.springframework.data.jpa.repository.JpaRepository

interface LoanRepository : JpaRepository<Loan, Long>