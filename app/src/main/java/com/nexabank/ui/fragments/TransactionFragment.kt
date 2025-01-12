package com.nexabank.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexabank.adapters.TransactionAdapter
import com.nexabank.databinding.FragmentTransactionBinding
import com.nexabank.ui.vms.TransactionViewModel
import com.nexabank.util.AlarmUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding
    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
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
        refreshTransactions()
    }

    /*
    private fun setupListeners() {
        // Show Bottom Sheets for each transaction option , im thinking an expanded floating action button
        with(binding) {

        }

        /*binding.btnCreateTransaction.setOnClickListener {
            val sender = "hesham@gmail.com"
            val recipient = binding.etRecipient.text.toString()
            val amount = binding.etAmount.text.toString().toDoubleOrNull()
            val description = binding.etDescription.text.toString()
            val category = binding.etCategory.text.toString()

            if (amount != null && amount > 0) {
                val request = TransactionRequest(
                    senderUsername = sender,
                    recipientUsername = recipient,
                    amount = amount,
                    description = description,
                    category = category
                )
                viewModel.createTransaction(sender, request)
                AlarmUtil.showSnackBar(binding.root, "Transaction created")
            } else {
                AlarmUtil.showSnackBar(binding.root, "Invalid amount")
            }
            */
    }
    */

    private fun setupRV() {
        val transactionAdapter = TransactionAdapter().apply {
            setOnItemClickListener {
                // Handle item click
                AlarmUtil.showSnackBar(
                    binding.root,
                    "Transaction for: ${it.recipient} with amount: ${it.amount} is clicked"
                )
            }
        }
        viewModel.spendingInsights {
            AlarmUtil.showSnackBar(binding.root, "Spending insights: $it")
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

    private fun refreshTransactions() {
        with(binding.transactionSwipeRefreshLayout) {
            setOnRefreshListener {
                viewModel.spendingInsights {
                    AlarmUtil.showSnackBar(binding.root, "Spending insights: $it")
                }
                isRefreshing = false
            }
        }
    }
}