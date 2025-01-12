package com.nexabank.ui.fragments.bottom_sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nexabank.core.AppSharedPreferences
import com.nexabank.databinding.FragmentTransferBottomSheetBinding
import com.nexabank.ui.vms.AccountViewModel
import com.nexabank.util.AlarmUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TransferBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTransferBottomSheetBinding

    @Inject
    lateinit var appSharedPreferences: AppSharedPreferences

    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransferBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setupListeners()
        viewModel.bind(binding)
        observeTransferResult()
    }

    private fun setupListeners() {
        binding.transferButton.setOnClickListener {
            val recipient = binding.recipientInput.text.toString()
            val amount = binding.amountInput.text.toString().toDoubleOrNull()
            val description = binding.descriptionInput.text.toString()

            if (recipient.isNotBlank() && amount != null && amount > 0) {
                viewModel.transferFunds(
                    appSharedPreferences.getUsername()!!,
                    recipient,
                    amount,
                    description
                )
            } else {
                AlarmUtil.showToast(requireContext(), "Invalid input")
            }
        }
    }

    private fun observeTransferResult() {
        viewModel.transferResult.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess) {
                AlarmUtil.showToast(requireContext(), result.getOrNull() ?: "Transfer Successful")
                this.dismiss()
            } else {
                AlarmUtil.showToast(requireContext(), result.exceptionOrNull()?.message ?: "Transfer Failed")
                // Show error message if needed
            }
        }
    }

}
