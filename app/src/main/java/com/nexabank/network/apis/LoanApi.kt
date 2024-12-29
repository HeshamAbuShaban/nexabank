package com.nexabank.network.apis

import com.nexabank.models.Loan
import com.nexabank.models.dto.LoanRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LoanApi {
    @POST("loans/{username}/request")
    suspend fun requestLoan(
        @Path("username") username: String,
        @Body request: LoanRequest // e.g., amount, repaymentPeriod
    ): Response<Loan>

    @GET("loans/{username}/status")
    suspend fun getLoanStatus(
        @Path("username") username: String
    ): Response<List<Loan>>

    @PUT("loans/{loanId}/review")
    suspend fun reviewLoan(
        @Path("loanId") loanId: Long,
        @Body body: LoanRequest
    ): Response<String>

    @POST("loans/{username}/repay/{loanId}")
    suspend fun repayLoan(
        @Path("username") username: String,
        @Path("loanId") loanId: Long
    ): Response<Unit>
}