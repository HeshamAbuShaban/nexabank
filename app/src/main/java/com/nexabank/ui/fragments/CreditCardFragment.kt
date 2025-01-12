package com.nexabank.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexabank.adapters.CreditCardAdapter
import com.nexabank.database.enums.CardType
import com.nexabank.databinding.FragmentCreditCardBinding
import com.nexabank.ui.vms.CreditCardViewModel
import com.nexabank.util.AlarmUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditCardFragment : Fragment() {
    private lateinit var binding: FragmentCreditCardBinding
    private val viewModel: CreditCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreditCardBinding.inflate(inflater, container, false)
        viewModel.setBinding(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.motionLayout.transitionToStart()
        setupRV()
        setupCCardDummyInfo()
    }

    private fun setupRV() {
        val creditCardAdapter = CreditCardAdapter().apply {
            setOnItemClickListener { creditCard ->
                // Handle item click
                AlarmUtil.showSnackBar(
                    binding.root,
                    "Credit card with: ${creditCard.cardNumber} number is clicked"
                )
            }
        }
        viewModel.getAllCards {
            creditCardAdapter.cards = it.toMutableList()
        }
        with(binding.rvCreditCards) {
            adapter = creditCardAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupCCardDummyInfo() {
        with(binding.creditCardView) {
            setCardHolderName("Hesham AbuShaban")
            setCardNumber("1234 5678 9012 3456")
            setExpiryDate("12/25")
            setCardType(CardType.Visa.name)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            binding.motionLayout.transitionToEnd()
        }, 580)
    }
}