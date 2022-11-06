package com.gabo.gk.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabo.gk.R
import com.gabo.gk.comon.constants.PRODUCT_GRID_VIEW
import com.gabo.gk.comon.constants.PRODUCT_LIST_VIEW
import com.gabo.gk.comon.extensions.loadImage
import com.gabo.gk.comon.extensions.loadImageDecreasedQuality
import com.gabo.gk.databinding.ProductItemGridViewBinding
import com.gabo.gk.databinding.ProductItemListViewBinding
import com.gabo.gk.ui.model.product.ProductModelUi

@SuppressLint("NotifyDataSetChanged")
class ProductsAdapter(
    private val currentUid: String,
    private val itemClick: (ProductModelUi) -> Unit,
    private val heartClick: (ProductModelUi) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<ProductModelUi> = emptyList()
    private var viewType = PRODUCT_LIST_VIEW
    fun changeView(view: String) {
        viewType = view
        notifyDataSetChanged()
    }

    fun submitList(list: List<ProductModelUi>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ProductListVH(private val binding: ProductItemListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            model: ProductModelUi,
            itemClick: (ProductModelUi) -> Unit,
            heartClick: (ProductModelUi) -> Unit
        ) {
            with(binding) {
                if (model.photos!!.isNotEmpty()) ivPoster.loadImageDecreasedQuality(model.photos[0])
                tvTitle.text = model.title
                tvCondition.text = model.productCondition
                tvPrice.text =
                    (if (model.negotiablePrice) tvPrice.context.getString(R.string.negotiable_price) else "$ ${model.price}")
                ivHeart.setImageResource(
                    if (model.isSaved.contains(currentUid)) R.drawable.ic_heart_filled else R.drawable.ic_heart
                )
                if (model.uid == currentUid) {
                    ivHeart.visibility = View.GONE
                } else if (model.purchasedBy == currentUid) ivHeart.visibility =
                    View.GONE else ivHeart.visibility = View.VISIBLE
                ivHeart.setOnClickListener { heartClick(model) }
            }
            itemView.setOnClickListener { itemClick(model) }
        }
    }

    inner class ProductGridVH(private val binding: ProductItemGridViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            model: ProductModelUi,
            click: (ProductModelUi) -> Unit,
            heartClick: (ProductModelUi) -> Unit
        ) {
            with(binding) {
                if (model.photos!!.isNotEmpty()) ivPoster.loadImage(model.photos[0])
                tvTitle.text = model.title
                tvCondition.text = model.productCondition
                tvPrice.text =
                    (if (model.negotiablePrice) tvPrice.context.getString(R.string.negotiable_price) else "$ ${model.price}")
                ivHeart.setImageResource(
                    if (model.isSaved.contains(currentUid)) R.drawable.ic_heart_filled else R.drawable.ic_heart
                )
                if (model.uid == currentUid) {
                    ivHeart.visibility = View.GONE
                } else if (model.purchasedBy == currentUid) ivHeart.visibility =
                    View.GONE else ivHeart.visibility = View.VISIBLE
                ivHeart.setOnClickListener { heartClick(model) }
            }
            itemView.setOnClickListener { click(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LIST_VIEW -> ProductListVH(
                ProductItemListViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ProductGridVH(
                ProductItemGridViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        when (getItemViewType(position)) {
            GRID_VIEW ->
                (holder as ProductGridVH).bind(item, itemClick, heartClick)
            LIST_VIEW -> (holder as ProductListVH).bind(item, itemClick, heartClick)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (viewType) {
            PRODUCT_GRID_VIEW -> GRID_VIEW
            else -> LIST_VIEW
        }
    }

    override fun getItemCount(): Int = list.size

    companion object {
        const val LIST_VIEW = 1
        const val GRID_VIEW = 2
    }
}