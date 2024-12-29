package com.nexabank.network.apis

import com.nexabank.database.entities.CreditCardEntity
import com.nexabank.models.CreditCard
import retrofit2.Response
import retrofit2.http.*

interface CreditCardApi {
    @POST("cards/{username}/generate")
    suspend fun generateCard(
        @Path("username") username: String
    ): Response<CreditCardEntity>

    @PUT("cards/{username}/{cardId}/toggle")
    suspend fun toggleCardFreeze(
        @Path("username") username: String,
        @Path("cardId") cardId: Long
    ): Response<Unit>

    @GET("cards/{username}")
    suspend fun getAllCards(
        @Path("username") username: String
    ): Response<List<CreditCard>>
}
