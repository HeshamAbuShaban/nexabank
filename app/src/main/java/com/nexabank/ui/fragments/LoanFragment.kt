package com.nexabank.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexabank.adapters.LoanAdapter
import com.nexabank.databinding.FragmentLoanBinding
import com.nexabank.ui.vms.LoanViewModel
import com.nexabank.util.AlarmUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoanFragment : Fragment() {
    private lateinit var binding: FragmentLoanBinding
    private val viewModel: LoanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoanBinding.inflate(inflater, container, false)
        viewModel.setBinding(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        /*setupListeners()*/
        setupRV()
        refreshLoans()
    }

    /*
    private fun setupListeners() {
     // Show Bottom Sheets for each loan option , im thinking an expanded floating action button
      with(binding) {btnCreeLoan.setOnClickListener {}}
    }
     */

    private fun setupRV() {
        val loanAdapter = LoanAdapter().apply {
            setOnItemClickListener {
                // Handle item click
                AlarmUtil.showSnackBar(
                    binding.root,
                    "Loan for: ${it.user.username} with amount: ${it.amount} and repayment period: ${it.repaymentPeriod} he still has: ${it.remainingBalance} to be repaid in: ${it.dueDate} is clicked"
                )
            }
        }
        viewModel.getLoans(onLoansRetrieved = { loans ->
            loanAdapter.loans = loans
        })
        with(binding.rvLoans) {
            adapter = loanAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun refreshLoans() {
        with(binding.loanSwipeRefreshLayout) {
            setOnRefreshListener {
                viewModel.getLoans(onLoansRetrieved = { loans ->
                    AlarmUtil.showSnackBar(binding.root, "Loan Test : ${loans[0]}")
                })
                isRefreshing = false
            }
        }
    }
}