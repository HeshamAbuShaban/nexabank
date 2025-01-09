package com.nexabank.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexabank.databinding.ItemCreditCardBinding
import com.nexabank.models.CreditCard

/**
 * Adapter for displaying credit card information in a RecyclerView.
 *
 * **Important:** To handle item clicks, you must call `setOnItemClickListener` and provide a listener.
 */
class CreditCardAdapter : RecyclerView.Adapter<CreditCardAdapter.CreditCardViewHolder>() {
    private val differ = AsyncListDiffer(this, CreditCardDiffCallback)

    var cards: MutableList<CreditCard>
        set(value) = differ.submitList(value)
        get() = differ.currentList

    private object CreditCardDiffCallback : DiffUtil.ItemCallback<CreditCard>() {
        override fun areItemsTheSame(oldItem: CreditCard, newItem: CreditCard): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CreditCard, newItem: CreditCard): Boolean =
            oldItem == newItem
    }

    /**
     * Sets the click listener for credit card items.
     *
     * @onItemClick The listener to be invoked when a credit card item is clicked.
     */
    private lateinit var onItemClick: (CreditCard) -> Unit
    fun setOnItemClickListener(listener: (CreditCard) -> Unit) {
        onItemClick = listener
    }

    inner class CreditCardViewHolder(private val binding: ItemCreditCardBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(card: CreditCard) {
            with(binding) {
                tvCardNumber.text = card.cardNumber
                tvCardExpiry.text = card.expirationDate
                tvCardType.text = card.cardType.name
            }
            itemView.setOnClickListener {
                if (::onItemClick.isInitialized) {
                    onItemClick(card)
                    notifyItemChanged(adapterPosition)
                } else {
                    throw IllegalStateException("setOnItemClickListener must be called before using the adapter.")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditCardViewHolder =
        CreditCardViewHolder(
            ItemCreditCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CreditCardViewHolder, position: Int) =
        holder.bind(cards[position])

    override fun getItemCount() = cards.size
}
