package com.nexabank.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.nexabank.R
import com.nexabank.databinding.ViewCreditCardBinding
import com.nexabank.util.flipCard

class CreditCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewCreditCardBinding =
        ViewCreditCardBinding.inflate(LayoutInflater.from(context), this, true)


    private var cardInfo: CardInfo = CardInfo(context) // Initialize with default values
        set(value) {
            field = value
            with(binding) {
                cardNumberTv.text = value.cardNumber
                cardHolderNameTv.text = value.cardHolderName
                tvExpiryDate.text = value.expiryDate
                cardTypeLogoTv.text = value.cardType
            }
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.CreditCardView, defStyleAttr, 0) {
            cardInfo = CardInfo(
                cardNumber = getString(R.styleable.CreditCardView_cardNumber),
                cardHolderName = getString(R.styleable.CreditCardView_cardHolderName),
                cardType = getString(R.styleable.CreditCardView_cardType),
                expiryDate = getString(R.styleable.CreditCardView_expiryDate),
                context = context
            )
            flipCard(this@CreditCardView)
        }
    }
}

data class CardInfo(
    val context: Context,
    val cardNumber: String? = context.getString(R.string.placeholder_card_number),
    val cardHolderName: String? = context.getString(R.string.placeholder_name),
    val expiryDate: String? = context.getString(R.string.placeholder_expire_date),
    val cardType: String? = context.getString(R.string.visa)
)