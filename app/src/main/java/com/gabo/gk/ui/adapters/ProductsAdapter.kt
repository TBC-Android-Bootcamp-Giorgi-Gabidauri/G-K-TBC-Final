package com.gabo.gk.ui.adapters

import com.gabo.gk.databinding.ProductItemViewBinding
import com.gabo.gk.ui.model.product.ProductModelUi
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabo.gk.comon.extensions.loadImage

class ProductsAdapter(private val click: (ProductModelUi) -> Unit) :
    RecyclerView.Adapter<ProductsAdapter.ProductVH>() {
    private var list: List<ProductModelUi> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ProductModelUi>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ProductVH(private val binding: ProductItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ProductModelUi, click: (ProductModelUi) -> Unit) {
            with(binding){
                if (model.photos!!.isNotEmpty()){
                    ivPoster.loadImage(model.photos[0])
                }
                tvTitle.text = model.title
                tvCondition.text = model.productCondition
                tvPrice.text = if(model.negotiablePrice){
                    "negotiable price"
                } else{
                    model.price
                }
            }
            itemView.setOnClickListener { click(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val binding =
            ProductItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductVH(binding)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        val item = list[position]
        holder.bind(item, click)
    }

    override fun getItemCount(): Int = list.size
}