package com.gabo.gk.ui.adapters

import com.gabo.gk.databinding.CategoryItemViewBinding
import com.gabo.gk.ui.model.category.CategoryModel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CategoriesAdapter(private val click: (CategoryModel) -> Unit) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryVH>() {
    private var list: List<CategoryModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CategoryModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class CategoryVH(private val binding: CategoryItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CategoryModel, click: (CategoryModel) -> Unit) {
            with(binding){
                ivImage.setImageResource(model.img)
                tvTitle.text = model.title
                if (adapterPosition == list.size-1){
                    divider.visibility = View.GONE
                }else{
                    divider.visibility = View.VISIBLE
                }
                itemView.setOnClickListener { click(model) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val binding =
            CategoryItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryVH(binding)
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        val item = list[position]
        holder.bind(item, click)
    }

    override fun getItemCount(): Int = list.size
}