package com.nexabank.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import com.nexabank.R
import com.nexabank.databinding.ViewBalanceCardBinding

class BalanceCard @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding: ViewBalanceCardBinding = ViewBalanceCardBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        init()
    }

    private fun init() {
        // Load attributes
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.BalanceCard, defStyleAttr, 0
        )

        val value = typedArray.getString(R.styleable.BalanceCard_value) ?: "$ To be Checked"
        // how can i reach this value from an fragment that uses our view :>
        /**
         * class DashboardFragment{
         * binding = FragmentDashboardBinding.inflate(inflater, container, false)
         *
         * binding.balanceCard.tvBalanceAmount.text = "value"
         * }*/

        // Set text
        setValue(value)

        typedArray.recycle()
    }

    fun setValue(value: String) {
        this.binding.tvBalanceAmount.text = value
    }
}