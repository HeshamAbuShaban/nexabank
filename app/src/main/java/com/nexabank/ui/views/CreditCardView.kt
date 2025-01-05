package com.nexabank.ui.views

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.nexabank.R
import com.nexabank.databinding.ViewCreditCardBinding

class CreditCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewCreditCardBinding =
        ViewCreditCardBinding.inflate(LayoutInflater.from(context), this, true)

    private var cardNumber: String? = null
        set(value) {
            field = value
            binding.cardNumberTv.text = value
        }

    private var cardHolderName: String? = null
        set(value) {
            field = value
            binding.cardHolderNameTv.text = value
        }

    private var expiryDate: String? = null
        set(value) {
            field = value
            binding.tvExpiryDate.text = value
        }

    init {
        // Load attributes (if any)
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.CreditCardView, defStyleAttr, 0)
            // Retrieve custom attributes here (if defined)
            cardNumber = typedArray.getString(R.styleable.CreditCardView_cardNumber)
            cardHolderName = typedArray.getString(R.styleable.CreditCardView_cardHolderName)
            expiryDate = typedArray.getString(R.styleable.CreditCardView_expiryDate)
            typedArray.recycle()

            // Set initial values (if any)
            with(binding) {
                cardNumberTv.text = cardNumber
                cardHolderNameTv.text = cardHolderName
                tvExpiryDate.text = expiryDate
                /*cardChip.setImageResource(R.drawable.ic_chip_fit)
                visaLogoTv.text = context.getString(R.string.visa)
                cardContainer.setBackgroundResource(R.drawable.shape_gradient_background)
                cardContainer.alpha = 0.5f
                cardContainer.translationY = 200f*/
                motionLayout.transitionToStart()
                Handler(context.mainLooper).postDelayed({
                    motionLayout.transitionToEnd()
                }, 700)
            }
        } ?: {
            // Set initial values (if any)
            cardNumber = context.getString(R.string.placeholder_card_number)
            cardHolderName = context.getString(R.string.placeholder_name)
            expiryDate = context.getString(R.string.placeholder_expire_date)
        }
    }
}