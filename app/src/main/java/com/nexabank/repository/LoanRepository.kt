package com.nexabank.repository

import com.nexabank.models.Loan
import com.nexabank.models.dto.LoanRequest
import com.nexabank.network.apis.LoanApi
import javax.inject.Inject

class LoanRepository @Inject constructor(private val api: LoanApi) {

    suspend fun requestLoan(username: String, request: LoanRequest): Result<Loan> {
        return try {
            val response = api.requestLoan(username, request)
            if (response.isSuccessful) Result.success(response.body()!!) else Result.failure(
                Exception("Loan request failed")
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLoanStatus(username: String): Result<List<Loan>> {
        return try {
            val response = api.getLoanStatus(username)
            if (response.isSuccessful) Result.success(response.body()!!) else Result.failure(
                Exception("Loan status retrieval failed")
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun reviewLoan(loanId: Long, body: LoanRequest): Result<String> {
        return try {
            val response = api.reviewLoan(loanId, body)
            if (response.isSuccessful) Result.success(response.body()!!) else Result.failure(
                Exception("Loan review failed")
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun repayLoan(username: String, loanId: Long): Result<Unit> {
        return try {
            val response = api.repayLoan(username, loanId)
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Repayment failed"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLoans(username: String): Result<List<Loan>> {
        return try {
            val response = api.getLoans(username)
            if (response.isSuccessful)
                Result.success(response.body()!!)
            else Result.failure(
                Exception("Loan retrieval failed")
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}