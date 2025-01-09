package com.nexabank.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nexabank.databinding.FragmentDashboardBinding
import com.nexabank.ui.fragments.bottom_sheets.TransferBottomSheetFragment
import com.nexabank.ui.vms.AccountViewModel
import com.nexabank.util.AlarmUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val accountViewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        setupVM()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setupListeners()
    }


    private fun setupVM() {
        accountViewModel.bind(binding)
        accountViewModel.setupBalance()
    }

    private fun setupListeners() {
        with(binding) {
            cardTransfer.setOnClickListener {
                TransferBottomSheetFragment().show(parentFragmentManager, "TransferBottomSheet")
                AlarmUtil.showSnackBar(binding.root, "Transfer clicked")
            }
            cardLoans.setOnClickListener {
                AlarmUtil.showSnackBar(binding.root, "Loans clicked")
            }
            cardPay.setOnClickListener {
                AlarmUtil.showSnackBar(binding.root, "Pay clicked")
            }
            cardTransaction.setOnClickListener {
                AlarmUtil.showSnackBar(binding.root, "Transactions clicked")
            }
            cardSettings.setOnClickListener {
                AlarmUtil.showSnackBar(binding.root, "Settings clicked")
            }
            cardCreditCard.setOnClickListener {
                AlarmUtil.showSnackBar(binding.root, "Credit Card clicked")
            }
        }
        refreshPage()
    }

    private fun refreshPage() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            val str = buildString {
                append("Refreshed at ")
                append(AlarmUtil.getCurrentTime())
            }
            binding.balanceCard.setValue("Refreshing...")
            accountViewModel.setupBalance()
            AlarmUtil.showSnackBar(binding.root, str)
            /*
            android.os.Handler(Looper.getMainLooper()).postDelayed({

            }, 2500)*/
        }
    }
}