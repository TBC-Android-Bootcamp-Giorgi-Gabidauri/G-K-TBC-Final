package com.gabo.gk.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabo.gk.R
import com.gabo.gk.comon.extensions.loadImageDecreasedQuality
import com.gabo.gk.databinding.TransactionItemViewBinding
import com.gabo.gk.ui.model.transaction.TransactionModelUi

class TransactionsAdapter(
    private val uid: String,
    private val itemClick: (TransactionModelUi) -> Unit
) :
    RecyclerView.Adapter<TransactionsAdapter.TransactionVH>() {
    private var list: List<TransactionModelUi> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<TransactionModelUi>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class TransactionVH(private val binding: TransactionItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: TransactionModelUi, itemClick: (TransactionModelUi) -> Unit) {
            with(binding) {
                tvTitle.text = model.title
                if (model.purchasedBy.contains(uid)) {
                    tvSold.text = tvSold.context.getString(R.string.purchased)
                    tvPrice.text = "- ${model.price} $"
                } else {
                    tvSold.text = tvSold.context.getString(R.string.sold)
                    tvPrice.text = "+ ${model.price} $"
                }
                ivPoster.loadImageDecreasedQuality(
                    model.images?.get(0) ?: R.drawable.ic_launcher_background
                )
            }
            itemView.setOnClickListener { itemClick(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionVH {
        val binding =
            TransactionItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionVH(binding)
    }

    override fun onBindViewHolder(holder: TransactionVH, position: Int) {
        val item = list[position]
        holder.bind(item, itemClick)
    }

    override fun getItemCount(): Int = list.size
}