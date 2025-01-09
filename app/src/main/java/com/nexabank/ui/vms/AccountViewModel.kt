package com.nexabank.ui.vms

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexabank.core.AppSharedPreferences
import com.nexabank.databinding.FragmentDashboardBinding
import com.nexabank.repository.AccountRepository
import com.nexabank.util.AlarmUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * ViewModel for managing account-related operations.
 * such as the deposit, withdrawal, and balance retrieval.
 * so basically it contain all the money related stuff
 * */
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val appSharedPreferences: AppSharedPreferences
) : ViewModel() {

    private lateinit var binding: FragmentDashboardBinding
    fun bind(binding: FragmentDashboardBinding) {
        this.binding = binding
        val username = appSharedPreferences.getUsername()
        binding.greetingTV.apply {
            text = buildString {
                append("Welcome back..., ")
                append(username)
            }
        }
    }

    fun depositFunds(username: String, amount: Double) {
        viewModelScope.launch {
            val result = accountRepository.depositFunds(username, amount)
            // Handle result (success or failure)
            result.onSuccess {
                // Deposit successful
                AlarmUtil.showSnackBar(binding.root, result.getOrNull() ?: "Deposit successful")
            }.onFailure {
                // Deposit failed
                AlarmUtil.showSnackBar(binding.root, result.exceptionOrNull()?.message ?: "Deposit failed")
            }
        }
    }

    fun withdrawFunds(username: String, amount: Double) {
        viewModelScope.launch {
            val result = accountRepository.withdrawFunds(username, amount)
            // Handle result (success or failure)
            result.onSuccess {
                // Withdrawal successful
                AlarmUtil.showSnackBar(binding.root, result.getOrNull() ?: "Withdrawal successful")
            }.onFailure {
                // Withdrawal failed
                AlarmUtil.showSnackBar(binding.root, result.exceptionOrNull()?.message ?: "Withdrawal failed")
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


    // what should be the name for this following method to take care of all the balance related stuff
    fun setupBalance() {
        viewModelScope.launch {
            try {
                val result = accountRepository.getBalance(appSharedPreferences.getUsername()!!)
                handleBalanceResult(result)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    /**
     * Handles the result of the balance retrieval operation.
     *
     * @param result The Result object containing either the balance or an error.
     */
    private fun handleBalanceResult(result: Result<Double>) {
        result.onSuccess { balance ->
            displayBalance(balance)
        }.onFailure { exception ->
            handleError(exception)
        }
    }

    /**
     * Displays the balance in the UI and shows a SnackBar.
     *
     * @param balance The account balance to display.
     */
    private fun displayBalance(balance: Double) {
        // Update UI with balance if needed
        val formattedBalance = buildString {
            append(balance)
            append("$")
        }
        binding.balanceCard.setValue(formattedBalance)
    }

    /**
     * Handles errors that occur during balance retrieval.
     *
     * @param exception The exception that occurred.
     */
    private fun handleError(exception: Throwable) {
        val errorMessage = buildErrorMessage(exception)
        AlarmUtil.showToast(binding.root.context, errorMessage)
        binding.balanceCard.setValue(errorMessage)
        Log.e("AccountViewModel", "checkBalance: error", exception)
    }

    /**
     * Builds a user-friendly error message based on the type of exception.
     *
     * @param exception The exception that occurred.
     * @return A user-friendly error message.
     */
    private fun buildErrorMessage(exception: Throwable): String {
        return when (exception) {
            is ConnectException -> "Failed to connect to the server. Check your network connection."
            is SocketTimeoutException -> "Connection timed out. The server might be slow or unavailable."
            is UnknownHostException -> "Unable to resolve the server's address. Check the server IP or domain name."
            else -> "An unexpected error occurred: ${exception.message ?: "Unknown error"}"
        }
    }
}