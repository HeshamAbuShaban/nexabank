package com.nexabank.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexabank.databinding.FragmentTransactionBinding
import com.nexabank.repository.AccountRepository
import com.nexabank.util.AlarmUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private lateinit var binding: FragmentTransactionBinding
    fun setBinding(binding: FragmentTransactionBinding) {
        this.binding = binding
    }

    fun depositFunds(username: String, amount: Double) {
        viewModelScope.launch {
            val result = accountRepository.depositFunds(username, amount)
            // Handle result (success or failure)
            result.onSuccess {
                // Deposit successful
                AlarmUtil.showSnackBar(binding.root, "Deposit successful")
            }.onFailure {
                // Deposit failed
                AlarmUtil.showSnackBar(binding.root, "Deposit failed")
            }
        }
    }

    fun withdrawFunds(username: String, amount: Double) {
        viewModelScope.launch {
            val result = accountRepository.withdrawFunds(username, amount)
            // Handle result (success or failure)
            result.onSuccess {
                // Withdrawal successful
                AlarmUtil.showSnackBar(binding.root, "Withdrawal successful")
            }.onFailure {
                // Withdrawal failed
                AlarmUtil.showSnackBar(binding.root, "Withdrawal failed")
            }
        }
    }

    fun transferFunds(senderUsername: String, receiverUsername: String, amount: Double) {
        viewModelScope.launch {
            /*
            val result = accountRepository.transferFunds(senderUsername, receiverUsername, amount)
            // Handle result (success or failure)
            result.onSuccess {
                // Transfer successful
                AlarmUtil.showSnackBar(binding.root, "Transfer successful")
            }.onFailure {
                // Transfer failed
                AlarmUtil.showSnackBar(binding.root, "Transfer failed")
            }
            */
        }
    }

    fun checkBalance(username: String) {
        viewModelScope.launch {
            /*
            val result = accountRepository.checkBalance(username)
            // Handle result (success or failure)
            result.onSuccess { balance ->
                // Display balance
                AlarmUtil.showSnackBar(binding.root, "Balance: $balance")
            }.onFailure {
                // Handle error
                AlarmUtil.showSnackBar(binding.root, "Error checking balance")
            }
            */
        }
    }

}
