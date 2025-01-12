package com.nexabank.ui.fragments.bottom_sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nexabank.core.AppSharedPreferences
import com.nexabank.databinding.FragmentDepositBottomSheetBinding
import com.nexabank.ui.vms.AccountViewModel
import com.nexabank.util.AlarmUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DepositBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDepositBottomSheetBinding

    @Inject
    lateinit var appSharedPreferences: AppSharedPreferences

    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDepositBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setupListeners()
        viewModel.bind(binding)
        observeDepositResult()
    }

    private fun setupListeners() {
        binding.depositButton.setOnClickListener {
            val amount = binding.amountInput.text.toString().toDoubleOrNull()
            if (amount == null || amount <= 0) {
                AlarmUtil.showToast(requireContext(), "Invalid amount")
                binding.amountInput.error = "Invalid amount"
                binding.amountInput.requestFocus()
                binding.amountInput.text?.clear()
                binding.amountInput.text?.append("0")
                binding.amountInput.setSelection(binding.amountInput.text?.length ?: 0)
                return@setOnClickListener
            }
            binding.amountInput.error = null
            binding.amountInput.clearFocus()
            viewModel.depositFunds(appSharedPreferences.getUsername()!!, amount)
            this.dismiss()
        }
    }

    private fun observeDepositResult() {
        viewModel.depositResult.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess) {
                AlarmUtil.showToast(requireContext(), result.getOrNull() ?: "Deposit Successful")
                this.dismiss()
            } else {
                AlarmUtil.showToast(
                    requireContext(),
                    result.exceptionOrNull()?.message ?: "Deposit Failed"
                )
                // Show error message if needed
            }
        }
    }

}
