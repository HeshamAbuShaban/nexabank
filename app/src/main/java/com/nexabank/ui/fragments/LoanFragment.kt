package com.nexabank.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexabank.adapters.LoanAdapter
import com.nexabank.databinding.FragmentLoanBinding
import com.nexabank.models.Loan
import com.nexabank.models.User
import com.nexabank.models.enums.Role
import com.nexabank.util.AlarmUtil

class LoanFragment : Fragment() {
    private lateinit var binding: FragmentLoanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setupRV()
    }

    private fun setupRV() {
        val loanAdapter = LoanAdapter().apply {
            setOnItemClickListener {
                // Handle item click
                AlarmUtil.showSnackBar(
                    binding.root,
                    "Loan for: ${it.user} with amount: ${it.amount} and repayment period: ${it.repaymentPeriod} he still has: ${it.remainingBalance} to be repaid in: ${it.dueDate} is clicked"
                )
            }
            loans = mutableListOf(
                Loan(
                    id = 1,
                    user = User(
                        id = 1,
                        username = "Hesham AbuShaban",
                        "hesham@gmail.com",
                        200000.00,
                        Role.USER
                    ),
                    amount = 2000.00,
                    repaymentPeriod = 12,
                    requestDate = "20/1/2025",
                    dueDate = null
                ), Loan(
                    id = 2,
                    user = User(
                        id = 2,
                        username = "Haneen AbuShaban",
                        "haneen@gmail.com",
                        100000.00,
                        Role.USER
                    ),
                    amount = 1000.00,
                    repaymentPeriod = 12,
                    requestDate = "20/1/2025",
                    dueDate = null
                ), Loan(
                    id = 3,
                    user = User(
                        id = 3,
                        username = "Ahmed AbuShaban",
                        "ahmed@gmail.com",
                        300000.00,
                        Role.USER
                    ),
                    amount = 3000.00,
                    repaymentPeriod = 12,
                    requestDate = "20/1/2025",
                    dueDate = null
                ), Loan(
                    id = 4,
                    user = User(
                        id = 4, username = "Bakr AbuShaban", "bakr@gmail.com", 300000.00, Role.USER
                    ),
                    amount = 4000.00,
                    repaymentPeriod = 12,
                    requestDate = "20/1/2025",
                    dueDate = null
                )
            )
        }
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
}