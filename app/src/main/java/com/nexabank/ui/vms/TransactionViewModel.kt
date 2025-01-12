package com.nexabank.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexabank.core.AppSharedPreferences
import com.nexabank.databinding.FragmentTransactionBinding
import com.nexabank.models.Transaction
import com.nexabank.models.dto.SpendingInsights
import com.nexabank.models.dto.TransactionRequest
import com.nexabank.repository.TransactionRepository
import com.nexabank.util.AlarmUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val appSharedPreferences: AppSharedPreferences
) :
    ViewModel() {

    private lateinit var binding: FragmentTransactionBinding
    fun setBinding(binding: FragmentTransactionBinding) {
        this.binding = binding
    }

    fun createTransaction(request: TransactionRequest) =
        viewModelScope.launch {
            val result = transactionRepository.createTransaction(appSharedPreferences.getUsername()!!, request)
            // Handle result (success or failure)
            result.onSuccess {
                // Transaction successful
                AlarmUtil.showSnackBar(binding.root, "Transaction successful")
            }.onFailure {
                // Transaction failed
                AlarmUtil.showSnackBar(binding.root, "Transaction failed")
            }
        }

    fun getTransactionHistory(onHistoryRetrieved: (List<Transaction>) -> Unit) =
        viewModelScope.launch {
            val result = transactionRepository.getTransactionHistory(appSharedPreferences.getUsername()!!)
            // Handle result (success or failure)
            result.onSuccess { history ->
                // Display transaction history
                onHistoryRetrieved(arrayListOf(history[0]))
            }.onFailure {
                // Handle error
                AlarmUtil.showSnackBar(binding.root, "Error getting transaction history")
            }
        }

    fun flagTransaction(transactionId: Long) =
        viewModelScope.launch {
            val result = transactionRepository.flagTransaction(transactionId)
            // Handle result (success or failure)
            result.onSuccess {
                // Transaction flagged
                AlarmUtil.showSnackBar(binding.root, "Transaction flagged")
            }.onFailure {
                // Handle error
                AlarmUtil.showSnackBar(binding.root, "Error flagging transaction")
            }
        }

    fun spendingInsights(onInsightsRetrieved: (SpendingInsights) -> Unit) = viewModelScope.launch {
        val result = transactionRepository.spendingInsights(appSharedPreferences.getUsername()!!)
        // Handle result (success or failure)
        result.onSuccess { insights ->
            onInsightsRetrieved(insights)
            // Display spending insights
            AlarmUtil.showSnackBar(binding.root, "Spending insights: $insights")
            }.onFailure {
            // Handle error
            AlarmUtil.showSnackBar(binding.root, "Error getting spending insights")
        }
    }

    /*
    fun deleteTransaction(transactionId: Long) =
        viewModelScope.launch {
            val result = transactionRepository.deleteTransaction(transactionId)
            // Handle result (success or failure)
            result.onSuccess {
                // Transaction deleted
                AlarmUtil.showSnackBar(binding.root, "Transaction deleted")
            }.onFailure {
                // Handle error
                AlarmUtil.showSnackBar(binding.root, "Error deleting transaction")
            }
    */
}