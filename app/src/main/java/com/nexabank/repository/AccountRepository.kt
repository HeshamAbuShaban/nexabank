package com.nexabank.repository

import com.nexabank.models.dto.DepositRequest
import com.nexabank.models.dto.WithdrawRequest
import com.nexabank.network.apis.AccountApi
import javax.inject.Inject

class AccountRepository @Inject constructor(private val api: AccountApi) {

    suspend fun depositFunds(username: String, amount: Double): Result<Unit> {
        return try {
            val depositRequest = DepositRequest(amount)
            val response = api.depositFunds(username, depositRequest)/*mapOf("amount" to amount)*/
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Deposit failed"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun withdrawFunds(username: String, amount: Double): Result<Unit> {
        return try {
            val withdrawRequest = WithdrawRequest(amount)
            val response = api.withdrawFunds(username, withdrawRequest)/*mapOf("amount" to amount)*/
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Withdrawal failed"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBalance(username: String): Result<Double> {
        return try {
            val response = api.getBalance(username)
            if (response.isSuccessful) Result.success(response.body() ?: 0.0) else Result.failure(
                Exception("Balance retrieval failed")
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}