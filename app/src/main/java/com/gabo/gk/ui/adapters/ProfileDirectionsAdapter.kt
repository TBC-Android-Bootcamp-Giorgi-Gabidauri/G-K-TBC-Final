package com.gabo.gk.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabo.gk.comon.extensions.loadImage
import com.gabo.gk.databinding.ProfileDirectionItemViewBinding
import com.gabo.gk.ui.model.profileDirection.ProfileDirectionModel

class ProfileDirectionsAdapter(private val itemClick: (ProfileDirectionModel) -> Unit) :
    RecyclerView.Adapter<ProfileDirectionsAdapter.ProfileDirectionVH>() {
    private var list: List<ProfileDirectionModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ProfileDirectionModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ProfileDirectionVH(private val binding: ProfileDirectionItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ProfileDirectionModel, itemClick: (ProfileDirectionModel) -> Unit) {
            with(binding){
                ivImage.loadImage(model.img)
                tvTitle.text = model.title
                tvInfo.text = model.info
            }
            itemView.setOnClickListener { itemClick(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileDirectionVH {
        val binding = ProfileDirectionItemViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProfileDirectionVH(binding)
    }

    override fun onBindViewHolder(holder: ProfileDirectionVH, position: Int) {
        val item = list[position]
        holder.bind(item, itemClick)
    }

    override fun getItemCount(): Int = list.size
}