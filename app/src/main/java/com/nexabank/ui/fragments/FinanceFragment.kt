package com.nexabank.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.nexabank.adapters.TabPagerAdapter
import com.nexabank.databinding.FragmentFinanceBinding
import com.nexabank.util.DepthPageTransformer

class FinanceFragment : Fragment() {
    private lateinit var binding: FragmentFinanceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(TransactionFragment())
        fragmentList.add(LoanFragment())
        fragmentList.add(CreditCardFragment())

        with(binding.viewPagerFinance) {
            adapter = TabPagerAdapter(requireParentFragment(), fragmentList)
            setPageTransformer(DepthPageTransformer())
            currentItem = 0
        }

    }

    private fun setupTabLayout() {
        TabLayoutMediator(
            binding.tabLayoutFinance, binding.viewPagerFinance
        ) { tab, position ->

            when (position) {
                0 -> tab.text = "Transaction"
                1 -> tab.text = "Loan"
                2 -> tab.text = "Credit Card"
            }

        }.attach()
    }
}