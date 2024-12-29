package com.nexabank.database.repo

import com.nexabank.database.dao.CreditCardDao
import com.nexabank.database.entities.CreditCardEntity
import javax.inject.Inject

class CreditCardEntityRepository @Inject constructor(private val creditCardDao: CreditCardDao) {
    suspend fun insertCreditCard(creditCard: CreditCardEntity) {
        creditCardDao.insertCreditCard(creditCard)
    }

    suspend fun getAllCards(): List<CreditCardEntity> {
        return creditCardDao.getAllCreditCards()
    }

    suspend fun getCardById(id: Long): CreditCardEntity? {
        return creditCardDao.getCreditCardById(id)
    }

    suspend fun updateCard(creditCard: CreditCardEntity) {
        creditCardDao.updateCreditCard(creditCard)
    }

    suspend fun deleteCardById(id: Long) {
        creditCardDao.deleteCreditCardById(id)
    }

    suspend fun deleteAllCards() {
        creditCardDao.deleteAllCreditCards()
    }

    suspend fun getCardByCardNumber(cardNumber: String): CreditCardEntity? {
        return creditCardDao.getCreditCardByCardNumber(cardNumber)
    }

}