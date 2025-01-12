package com.nexabank.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexabank.R
import com.nexabank.databinding.ItemTransactionBinding
import com.nexabank.models.Transaction
import com.nexabank.models.enums.TransactionStatus

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    private val differ = AsyncListDiffer(this, TransactionDiffCallback)

    private object TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
            oldItem == newItem
    }

    var transactions: List<Transaction>
        set(value) = differ.submitList(value)
        get() = differ.currentList

    /**
     * The click listener for transaction items.
     */
    private lateinit var onItemClick: (Transaction) -> Unit

    /**
     * Sets the click listener for transaction items.
     *
     * @param listener The listener to be invoked when a transaction item is clicked.
     */
    fun setOnItemClickListener(listener: (Transaction) -> Unit) {
        onItemClick = listener
    }

    private val allTransactions = transactions
    private val completedTransactions =
        transactions.filter { it.status == TransactionStatus.COMPLETED }
    private val pendingTransactions = transactions.filter { it.status == TransactionStatus.PENDING }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(transaction: Transaction) {
            with(binding) {
                tvTransactionDate.text = transaction.date ?: "Unknown"
                tvTransactionType.text = transaction.type?.name ?: "Unknown"
                tvTransactionAmount.text = "$${transaction.amount}"
                tvTransactionStatus.text = transaction.status.name
                tvTransactionStatus.setTextColor(
                    when (transaction.status) {
                        TransactionStatus.COMPLETED -> itemView.context.getColor(R.color.md_theme_onPrimaryContainer)
                        TransactionStatus.PENDING -> itemView.context.getColor(R.color.light_blue_400)
                        else -> itemView.context.getColor(R.color.md_theme_errorContainer_highContrast)
                    }
                )
            }

            itemView.setOnClickListener {
                if (::onItemClick.isInitialized) {
                    onItemClick(transaction)
                } else {
                    throw IllegalStateException("setOnItemClickListener must be called before using the adapter.")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount() = transactions.size
}
