package com.nexabank.network.apis

import com.nexabank.models.Transaction
import com.nexabank.models.dto.SpendingInsights
import com.nexabank.models.dto.TransactionRequest
import retrofit2.Response
import retrofit2.http.*

interface TransactionApi {
    @POST("transactions/{username}/create")
    suspend fun createTransaction(
        @Path("username") username: String,
        @Body request: TransactionRequest // e.g., recipient, amount, description
    ): Response<Transaction>

    @GET("transactions/{username}/history")
    suspend fun getTransactionHistory(
        @Path("username") username: String
    ): Response<List<Transaction>>

    @PUT("transactions/{transactionId}/flag")
    suspend fun flagTransaction(
        @Path("transactionId") transactionId: Long
    ): Response<Unit>

    @GET("transactions/{username}/spending-insights")
    suspend fun spendingInsights(@Path("username") username: String): Response<SpendingInsights>
}