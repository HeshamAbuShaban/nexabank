package com.nexabank.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nexabank.R
import com.nexabank.databinding.ItemLoanBinding
import com.nexabank.models.Loan
import com.nexabank.models.enums.LoanStatus

class LoanAdapter : RecyclerView.Adapter<LoanAdapter.LoanViewHolder>() {
    private lateinit var binding: ItemLoanBinding

/*
    private val allLoans = loans
    private val completedLoans = loans.filter { it.status == LoanStatus.APPROVED }
    private val pendingLoans = loans.filter { it.status == LoanStatus.PENDING }
*/

    private val differ = AsyncListDiffer(this, LoanDiffCallback)

    private object LoanDiffCallback : DiffUtil.ItemCallback<Loan>() {
        override fun areItemsTheSame(oldItem: Loan, newItem: Loan): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Loan, newItem: Loan): Boolean =
            oldItem == newItem
    }

    var loans: List<Loan>
        set(value) = differ.submitList(value)
        get() = differ.currentList

    private lateinit var onItemClick: (Loan) -> Unit
    fun setOnItemClickListener(listener: (Loan) -> Unit) {
        onItemClick = listener
    }

    inner class LoanViewHolder(private val binding: ItemLoanBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(loan: Loan) {
            with(binding) {
                tvLoanAmount.text = "$${loan.amount}"
                tvLoanStatus.text = loan.status.name
                tvRemainingBalance.text = "Remaining: $${loan.remainingBalance}"
                tvDueDate.text = "Due: ${loan.dueDate}"
                tvLoanStatus.setTextColor(
                    when (loan.status) {
                        LoanStatus.APPROVED -> itemView.context.getColor(R.color.md_theme_onPrimaryContainer)
                        LoanStatus.PENDING -> itemView.context.getColor(R.color.light_blue_400)
                        else -> itemView.context.getColor(R.color.md_theme_errorContainer_highContrast)
                    }
                )
            }


            itemView.setOnClickListener {
                if (::onItemClick.isInitialized) {
                    onItemClick(loan)
                    notifyItemChanged(adapterPosition)
                } else {
                    throw IllegalStateException("setOnItemClickListener must be called before using the adapter.")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        binding = ItemLoanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        holder.bind(loans[position])
    }

    override fun getItemCount() = loans.size
}

