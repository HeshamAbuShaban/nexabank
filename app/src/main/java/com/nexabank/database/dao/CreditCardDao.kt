package com.nexabank.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nexabank.database.entities.CreditCardEntity

@Dao
interface CreditCardDao {

    @Insert
    suspend fun insertCreditCard(card: CreditCardEntity)

    @Query("SELECT * FROM credit_cards WHERE id = :id")
    suspend fun getCreditCardById(id: Long): CreditCardEntity?

    @Query("SELECT * FROM credit_cards")
    suspend fun getAllCreditCards(): List<CreditCardEntity>

    @Update
    suspend fun updateCreditCard(card: CreditCardEntity)

    @Query("DELETE FROM credit_cards WHERE id = :id")
    suspend fun deleteCreditCardById(id: Long)

    @Query("DELETE FROM credit_cards")
    suspend fun deleteAllCreditCards()

    @Query("SELECT * FROM credit_cards WHERE cardNumber = :cardNumber")
    suspend fun getCreditCardByCardNumber(cardNumber: String): CreditCardEntity?

}
