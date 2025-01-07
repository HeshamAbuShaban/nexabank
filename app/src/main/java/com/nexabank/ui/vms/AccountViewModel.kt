package com.nexabank.ui.vms

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexabank.databinding.FragmentDashboardBinding
import com.nexabank.repository.AccountRepository
import com.nexabank.util.AlarmUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private lateinit var binding: FragmentDashboardBinding
    fun setBinding(binding: FragmentDashboardBinding) {
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

    fun checkBalance(username: String, callback: (Double) -> Unit) {
        viewModelScope.launch {

            val result = accountRepository.getBalance(username)
            // Handle result (success or failure)
            result.onSuccess { balance ->
                // Display balance
                AlarmUtil.showSnackBar(binding.root, "Balance: $balance")
                callback(balance)
            }.onFailure {
                // Handle error
                val errorMessage =
                    "Balance retrieval failed: msg:${it.message} ,stack:${it.stackTrace}, cause:${it.cause}, ${it.localizedMessage}"
                AlarmUtil.showToast(
                    binding.root.context,
                    "Balance retrieval failed: msg:${it.message}, Cause: ${it.cause} "
                )
                val tempErrorMsg = when (it) {
                    is ConnectException -> "Failed to connect to the server. Check your network connection."
                    is SocketTimeoutException -> "Connection timed out. The server might be slow or unavailable."
                    is UnknownHostException -> "Unable to resolve the server's address. Check the server IP or domain name."
                    else -> "An unexpected error occurred: ${it.message}"
                }
                binding.balanceAndRecentTransaction.tvBalanceAmount.text = tempErrorMsg
                Log.e("AVM", "checkBalance: error", it)
                callback(0.0)
            }

        }
    }

}
