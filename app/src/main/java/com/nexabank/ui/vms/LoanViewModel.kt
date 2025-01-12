package com.nexabank.ui.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexabank.core.AppSharedPreferences
import com.nexabank.databinding.FragmentLoanBinding
import com.nexabank.models.Loan
import com.nexabank.models.dto.LoanRequest
import com.nexabank.repository.LoanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(
    private val loanRepository: LoanRepository,
    private val appSharedPreferences: AppSharedPreferences
) : ViewModel() {
    //    private lateinit var binding: FragmentLoanBinding
    fun setBinding(binding: FragmentLoanBinding) {
//        this.binding = binding
    }

    fun requestLoan(username: String, request: LoanRequest) {
        viewModelScope.launch {
            val result = loanRepository.requestLoan(username, request)
            // Handle result (success or failure)
            result.onSuccess {
                // Loan request successful
//                AlarmUtil.showSnackBar(binding.rvLoans, "Loan request successful")
            }.onFailure {
                // Loan request failed
//                AlarmUtil.showSnackBar(binding.rvLoans, "Loan request failed")
            }
        }
    }

    fun getLoanStatus(username: String) {
        viewModelScope.launch {
            val result = loanRepository.getLoanStatus(username)
            // Handle result (success or failure)
            result.onSuccess { status ->
                // Display loan status
//                AlarmUtil.showSnackBar(binding.rvLoans, "Loan status: $status")
            }.onFailure {
                // Handle error
//                AlarmUtil.showSnackBar(binding.rvLoans, "Error getting loan status")
            }
        }
    }

    fun reviewLoan(loanId: Long, body: LoanRequest) {
        viewModelScope.launch {
            val result = loanRepository.reviewLoan(loanId, body)
            // Handle result (success or failure)
            result.onSuccess {
                // Loan review successful
//                AlarmUtil.showSnackBar(binding.rvLoans, "Loan review successful")
            }.onFailure {
                // Loan review failed
//                AlarmUtil.showSnackBar(binding.rvLoans, "Loan review failed")
            }
        }
    }

    fun repayLoan(username: String, loanId: Long) {
        viewModelScope.launch {
            val result = loanRepository.repayLoan(username, loanId)
            // Handle result (success or failure)
            result.onSuccess {
                // Loan repayment successful
//                AlarmUtil.showSnackBar(binding.rvLoans, "Loan repayment successful")
            }.onFailure {
                // Loan repayment failed
//                AlarmUtil.showSnackBar(binding.rvLoans, "Loan repayment failed")
            }
        }
    }

    fun getLoans(onLoansRetrieved: (List<Loan>) -> Unit) {
        viewModelScope.launch {
            val result = loanRepository.getLoans(appSharedPreferences.getUsername()!!)
            // Handle result (success or failure)
            result.onSuccess { loans ->
                // Display loans
                onLoansRetrieved(arrayListOf(loans[0]))
//                AlarmUtil.showSnackBar(binding.rvLoans, "Loans retrieved successfully")
            }.onFailure {
                // Handle error
//                AlarmUtil.showSnackBar(binding.rvLoans, "Error getting loans")
            }
        }
    }
}