package com.nexabank.repository

import com.nexabank.models.Transaction
import com.nexabank.models.dto.SpendingInsights
import com.nexabank.models.dto.TransactionRequest
import com.nexabank.network.apis.TransactionApi
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val api: TransactionApi) {

    suspend fun createTransaction(
        username: String,
        request: TransactionRequest
    ): Result<Transaction> {
        return try {
            val response = api.createTransaction(username, request)
            if (response.isSuccessful) Result.success(response.body()!!) else Result.failure(
                Exception("Transaction failed")
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTransactionHistory(username: String): Result<List<Transaction>> {
        return try {
            val response = api.getTransactionHistory(username)
            if (response.isSuccessful) Result.success(response.body()!!) else Result.failure(
                Exception("History retrieval failed")
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun flagTransaction(transactionId: Long): Result<Unit> {
        return try {
            val response = api.flagTransaction(transactionId)
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Flagging failed"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun spendingInsights(username: String): Result<SpendingInsights> {
        return try {
            val response = api.spendingInsights(username)
            if (response.isSuccessful) Result.success(response.body()!!) else Result.failure(
                Exception("Spending insights retrieval failed")
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /*
suspend fun deleteTransaction(transactionId: Long): Result<Unit> {
    return try {
        val response = api.deleteTransaction(transactionId)
        if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Deletion failed"))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
     */
}