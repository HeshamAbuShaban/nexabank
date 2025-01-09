package com.nexabank.repository

import com.nexabank.models.dto.DepositRequest
import com.nexabank.models.dto.TransferRequest
import com.nexabank.models.dto.WithdrawRequest
import com.nexabank.network.apis.AccountApi
import javax.inject.Inject

class AccountRepository @Inject constructor(private val api: AccountApi) {

    private var cachedBalance: Double? = null
    private var lastFetchTime: Long? = null
    private val cacheExpiryDuration: Long = 5 * 60 * 1000 // 5 minutes in milliseconds

    suspend fun depositFunds(username: String, amount: Double): Result<String> {
        return try {
            val depositRequest = DepositRequest(amount)
            val response = api.depositFunds(username, depositRequest)/*mapOf("amount" to amount)*/
            if (response.isSuccessful) {
                clearCache() // Clear the cache after a successful deposit
                Result.success(response.body() ?: "Deposit successful")
            } else Result.failure(Exception("Deposit failed"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun withdrawFunds(username: String, amount: Double): Result<String> {
        return try {
            val withdrawRequest = WithdrawRequest(amount)
            val response = api.withdrawFunds(username, withdrawRequest)/*mapOf("amount" to amount)*/
            if (response.isSuccessful) {
                clearCache() // Clear the cache after a successful withdrawal
                Result.success(response.body() ?: "Withdrawal successful")
            } else Result.failure(Exception("Withdrawal failed"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBalance(username: String): Result<Double> {
        val currentTime = System.currentTimeMillis()

        // Check if the cached balance is still valid
        if (cachedBalance != null && lastFetchTime != null &&
            (currentTime - lastFetchTime!!) < cacheExpiryDuration
        ) {
            return Result.success(cachedBalance!!)
        }

        // Fetch from API if cache is expired or not set
        return try {
            val response = api.getBalance(username)
            if (response.isSuccessful) {
                val balance = response.body() ?: 0.0
                // Update cache
                cachedBalance = balance
                lastFetchTime = currentTime
                Result.success(balance)
            } else {
                Result.failure(Exception("Balance retrieval failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun transferFunds(
        senderUsername: String,
        receiverUsername: String,
        amount: Double,
        description: String?
    ): Result<String> {
        return try {
            val transferRequest = TransferRequest(receiverUsername, amount, description)
            val response = api.transferFunds(senderUsername, transferRequest)
            if (response.isSuccessful) {
                clearCache() // Clear the cache after a successful transfer
                Result.success(response.body() ?: "Transfer successful")
            } else Result.failure(response.errorBody()?.string()?.let { Exception(it) } ?: Exception("Transfer failed"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Method to clear the cache explicitly, if needed
    private fun clearCache() {
        cachedBalance = null
        lastFetchTime = null
    }
}