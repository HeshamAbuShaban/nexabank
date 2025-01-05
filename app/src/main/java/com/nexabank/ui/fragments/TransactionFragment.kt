package com.nexabank.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexabank.adapters.TransactionAdapter
import com.nexabank.databinding.FragmentTransactionBinding
import com.nexabank.models.Transaction
import com.nexabank.models.User
import com.nexabank.models.enums.Role
import com.nexabank.models.enums.TransactionStatus
import com.nexabank.models.enums.TransactionType
import com.nexabank.util.AlarmUtil

class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
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
        val transactionAdapter = TransactionAdapter().apply {
            setOnItemClickListener {
                // Handle item click
                AlarmUtil.showSnackBar(
                    binding.root,
                    "Transaction for: ${it.recipient} with amount: ${it.amount} is clicked"
                )
            }
            val users = mutableListOf(
                User(
                    id = 1,
                    username = "Hesham AbuShaban",
                    "hesham@gmail.com",
                    200000.00,
                    Role.USER
                ),
                User(
                    id = 2,
                    username = "Haneen AbuShaban",
                    "haneen@gmail.com",
                    100000.00,
                    Role.USER
                ),
                User(
                    id = 3,
                    username = "Ahmed AbuShaban",
                    "ahmed@gmail.com",
                    300000.00,
                    Role.USER
                ),
                User(
                    id = 4,
                    username = "Electrical Engineering Company",
                    "mail@electrical.gov",
                    300000.00,
                    Role.USER
                ),
                User(
                    id = 5,
                    username = "Mechanical Engineering Company",
                    "mail@mechanical.gov",
                    300000.00,
                    Role.USER
                ),
                User(
                    id = 6,
                    username = "Civil Engineering Company",
                    "mail@civil.gov",
                    300000.00,
                    Role.USER
                ),
                User(
                    id = 7,
                    username = "Bank",
                    "mail@bank.com",
                    300000.00,
                    Role.USER
                )
            )
            transactions = mutableListOf(
                Transaction(
                    id = 1,
                    sender = users[0],
                    recipient = users[1],
                    amount = 20.000,
                    description = "This is a humble gift for my lovely wife",
                    TransactionStatus.PENDING,
                    date = "20/1/2025",
                    type = TransactionType.Transfer
                ),
                Transaction(
                    id = 2,
                    sender = users[0],
                    recipient = users[2],
                    amount = 10.000,
                    description = "This is a humble gift for my lovely brother in law",
                    status = TransactionStatus.PENDING,
                    date = "20/1/2025",
                    type = TransactionType.Transfer
                ),
                Transaction(
                    id = 3,
                    sender = users[0],
                    recipient = users[3],
                    amount = 40.5,
                    description = "This is a payment for my electricity bill",
                    status = TransactionStatus.PENDING,
                    date = "1/1/2025",
                    type = TransactionType.Payment
                ),
                Transaction(
                    id = 4,
                    sender = users[0],
                    recipient = users[4],
                    amount = 50.0,
                    description = "This is a payment for my gas bill",
                    status = TransactionStatus.PENDING,
                    date = "1/1/2025",
                    type = TransactionType.Payment
                ),
                Transaction(
                    id = 5,
                    sender = users[0],
                    recipient = users[5],
                    amount = 250.0,
                    description = "This is a payment for my water bill",
                    status = TransactionStatus.PENDING,
                    date = "1/1/2025",
                    type = TransactionType.Payment
                )
            )
        }
        with(binding.rvTransactions) {
            adapter = transactionAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }
}