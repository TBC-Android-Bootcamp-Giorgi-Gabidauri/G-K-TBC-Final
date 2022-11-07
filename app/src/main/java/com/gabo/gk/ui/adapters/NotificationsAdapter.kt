package com.gabo.gk.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabo.gk.comon.extensions.loadImageDecreasedQuality
import com.gabo.gk.databinding.NotificationItemViewBinding
import com.gabo.gk.domain.model.NotificationModel

class NotificationsAdapter(private val itemClick: (NotificationModel) -> Unit) :
    RecyclerView.Adapter<NotificationsAdapter.NotificationVH>() {
    private var list: List<NotificationModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<NotificationModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class NotificationVH(private val binding: NotificationItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: NotificationModel, itemClick: (NotificationModel) -> Unit) {
            with(binding){
                ivClient.loadImageDecreasedQuality(model.imgClient)
                ivProduct.loadImageDecreasedQuality(model.imgProduct)
                tvTitle.text = model.info
            }
            itemView.setOnClickListener { itemClick(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationVH {
        val binding =
            NotificationItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationVH(binding)
    }

    override fun onBindViewHolder(holder: NotificationVH, position: Int) {
        val item = list[position]
        holder.bind(item, itemClick)
    }

    override fun getItemCount(): Int = list.size
}