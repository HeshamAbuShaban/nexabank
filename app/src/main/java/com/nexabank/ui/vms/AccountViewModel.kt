package com.nexabank.ui.vms

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import com.nexabank.core.AppSharedPreferences
import com.nexabank.databinding.FragmentDashboardBinding
import com.nexabank.databinding.FragmentTransferBottomSheetBinding
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

    private lateinit var binding: ViewBinding
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

    fun bind(binding: FragmentTransferBottomSheetBinding) {
        this.binding = binding
    }

    fun bind(binding: ViewBinding) {
        this.binding = binding
    }

    // To be subscribed to in the fragment
    private val _transferResult = MutableLiveData<Result<String>>()
    val transferResult: LiveData<Result<String>> = _transferResult

    // To be subscribed to in the fragment
    private val _depositResult = MutableLiveData<Result<String>>()
    val depositResult: LiveData<Result<String>> = _depositResult

    // To be subscribed to in the fragment
    private val _withdrawalResult = MutableLiveData<Result<String>>()
    val withdrawalResult: LiveData<Result<String>> = _withdrawalResult

    // To be subscribed to in the fragment
    private val _balanceResult = MutableLiveData<Result<Double>>()
    val balanceResult: LiveData<Result<Double>> = _balanceResult

    // To be subscribed to in the fragment
    private val _logoutResult = MutableLiveData<Result<String>>()
    val logoutResult: LiveData<Result<String>> = _logoutResult


    fun depositFunds(username: String, amount: Double) {
        viewModelScope.launch {
            val result = accountRepository.depositFunds(username, amount)
            // Handle result (success or failure)
            result.onSuccess {
                // Deposit successful
                val value = result.getOrNull() ?: "Deposit successful"
                AlarmUtil.showSnackBar(binding.root, value)
                _depositResult.value = Result.success(value)
            }.onFailure {
                // Deposit failed
                AlarmUtil.showSnackBar(
                    binding.root,
                    result.exceptionOrNull()?.message ?: "Deposit failed"
                )
                _depositResult.value = Result.failure(it)
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
                AlarmUtil.showSnackBar(
                    binding.root,
                    result.exceptionOrNull()?.message ?: "Withdrawal failed"
                )
            }
        }
    }

    fun transferFunds(
        senderUsername: String,
        receiverUsername: String,
        amount: Double,
        description: String?
    ) {
        viewModelScope.launch {
            val bindTransferBottomSheet = binding as FragmentTransferBottomSheetBinding
            val result = accountRepository.transferFunds(
                senderUsername,
                receiverUsername,
                amount,
                description
            )
            // Handle result (success or failure)
            result.onSuccess {
                // Transfer successful
                AlarmUtil.showSnackBar(bindTransferBottomSheet.root, "Transfer successful")
                _transferResult.value = Result.success("Transfer successful")
            }.onFailure {
                // Transfer failed
                AlarmUtil.showSnackBar(bindTransferBottomSheet.root, "Transfer failed")
                _transferResult.value = Result.failure(it)
            }
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
        val bindDash = binding as FragmentDashboardBinding
        bindDash.balanceCard.setValue(formattedBalance)
    }

    /**
     * Handles errors that occur during balance retrieval.
     *
     * @param exception The exception that occurred.
     */
    private fun handleError(exception: Throwable) {
        val errorMessage = buildErrorMessage(exception)
        AlarmUtil.showToast(binding.root.context, errorMessage)
        val bindDash = binding as FragmentDashboardBinding
        bindDash.balanceCard.setValue(errorMessage)
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