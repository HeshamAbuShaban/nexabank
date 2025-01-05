package com.nexabank.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexabank.adapters.CreditCardAdapter
import com.nexabank.database.enums.CardType
import com.nexabank.databinding.FragmentCreditCardBinding
import com.nexabank.models.CreditCard
import com.nexabank.util.AlarmUtil

class CreditCardFragment : Fragment() {
    private lateinit var binding: FragmentCreditCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreditCardBinding.inflate(inflater, container, false)
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
        val creditCardAdapter = CreditCardAdapter().apply {
            setOnItemClickListener { creditCard ->
                // Handle item click
                AlarmUtil.showSnackBar(
                    binding.root,
                    "Credit card with: ${creditCard.cardNumber} number is clicked"
                )
            }
            cards = mutableListOf(
                CreditCard(
                    id = 1,
                    cardNumber = "1234 5678 9012 3456",
                    expirationDate = "01/25",
                    cardType = CardType.Visa,
                    cvv = "123",
                    balance = 1000.0,
                    isFrozen = false
                ),
                CreditCard(
                    id = 2,
                    cardNumber = "9876 5432 1098 7654",
                    expirationDate = "02/24",
                    cardType = CardType.MasterCard,
                    cvv = "456",
                    balance = 2000.0,
                    isFrozen = true
                ),
                CreditCard(
                    id = 3,
                    cardNumber = "5678 1234 5678 9012",
                    expirationDate = "03/25",
                    cardType = CardType.AMEX,
                    cvv = "789",
                    balance = 3000.0,
                    isFrozen = false
                )
            )
        }
        with(binding.rvCreditCards) {
            adapter = creditCardAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}