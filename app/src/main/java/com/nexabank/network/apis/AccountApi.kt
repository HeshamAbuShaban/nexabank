package com.nexabank.network.apis

import com.nexabank.models.dto.DepositRequest
import com.nexabank.models.dto.WithdrawRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountApi {
    @POST("accounts/{username}/deposit")
    suspend fun depositFunds(
        @Path("username") username: String,
        @Body request: DepositRequest
    ): Response<String>

    @POST("accounts/{username}/withdraw")
    suspend fun withdrawFunds(
        @Path("username") username: String,
        @Body request: WithdrawRequest
    ): Response<String>

    @GET("accounts/{username}/balance")
    suspend fun getBalance(
        @Path("username") username: String
    ): Response<Double>
}