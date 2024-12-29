package com.nexabank.repository

import com.nexabank.database.entities.CreditCardEntity
import com.nexabank.models.CreditCard
import com.nexabank.network.apis.CreditCardApi
import javax.inject.Inject

class CreditCardRepository @Inject constructor(private val api: CreditCardApi) {

    suspend fun generateCard(username: String): Result<CreditCardEntity> {
        return try {
            val response = api.generateCard(username)
            if (response.isSuccessful) Result.success(response.body()!!) else Result.failure(
                Exception("Card generation failed")
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun toggleCardFreeze(username: String, cardId: Long): Result<Unit> {
        return try {
            val response = api.toggleCardFreeze(username, cardId)
            if (response.isSuccessful) Result.success(Unit) else Result.failure(Exception("Failed to toggle card status"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllCards(username: String): Result<List<CreditCard>> {
        return try {
            val response = api.getAllCards(username)
            if (response.isSuccessful) Result.success(response.body()!!) else Result.failure(
                Exception("Failed to retrieve cards")
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
