package com.nexabank.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexabank.database.dao.CreditCardDao
import com.nexabank.database.repo.CreditCardEntityRepository
import com.nexabank.databinding.FragmentCreditCardBinding
import com.nexabank.repository.CreditCardRepository
import com.nexabank.util.AlarmUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditCardViewModel @Inject constructor(private val creditCardRepository: CreditCardRepository,private val creditCardEntityRepository: CreditCardEntityRepository) :
    ViewModel() {
    private lateinit var binding: FragmentCreditCardBinding
    fun setBinding(binding: FragmentCreditCardBinding) {
        this.binding = binding
    }

    @Inject
    lateinit var dao: CreditCardDao

    fun generateCard(username: String) {
        viewModelScope.launch {
            val result = creditCardRepository.generateCard(username)
            // Handle result (success or failure)
            result.onSuccess {
                // Card generation successful
                AlarmUtil.showSnackBar(
                    binding.root,
                    "Card generation successful.\n Card Details: " + result.getOrNull()
                )
                if (result.getOrNull() != null){
                    creditCardEntityRepository.insertCreditCard(result.getOrThrow())
                }
            }.onFailure {
                // Card generation failed
                AlarmUtil.showSnackBar(binding.root, "Card generation failed")
            }
        }
    }

    fun toggleCardFreeze(username: String, cardId: Long) {
        viewModelScope.launch {
            val result = creditCardRepository.toggleCardFreeze(username, cardId)
            // Handle result (success or failure)
            result.onSuccess {
                // Card freeze toggle successful
                val card = creditCardEntityRepository.getCardById(cardId)
                card?.isFrozen = !card?.isFrozen!!
                creditCardEntityRepository.updateCard(card)
                AlarmUtil.showSnackBar(binding.root, "Card freeze toggle successful")
            }.onFailure {
                // Card freeze toggle failed
                AlarmUtil.showSnackBar(binding.root, "Card freeze toggle failed")
            }
        }
    }

    fun getAllCards(username: String) {
        viewModelScope.launch {
            val result = creditCardRepository.getAllCards(username)
            // Handle result (success or failure)
            result.onSuccess { cards ->
                // Display all cards
                AlarmUtil.showSnackBar(binding.root, "All cards: $cards")
            }.onFailure {
                // Handle error
                AlarmUtil.showSnackBar(binding.root, "Error getting all cards")
            }
        }
    }
}