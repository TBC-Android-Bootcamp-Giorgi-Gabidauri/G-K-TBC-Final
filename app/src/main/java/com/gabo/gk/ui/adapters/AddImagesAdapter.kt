package com.gabo.gk.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabo.gk.comon.extensions.loadImage
import com.gabo.gk.databinding.ImageItemViewBinding
import com.gabo.gk.ui.model.addImage.ImageModel

class AddImagesAdapter(
    private val addClick: (ImageModel) -> Unit,
    private val deleteClick: (ImageModel) -> Unit
) :
    RecyclerView.Adapter<AddImagesAdapter.ImageVH>() {
    private var list: List<ImageModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ImageModel>) {
        val new = list.toMutableList()
        new.add(ImageModel())
        this.list = new.toList()
        notifyDataSetChanged()
    }

    inner class ImageVH(private val binding: ImageItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            model: ImageModel,
            addClick: (ImageModel) -> Unit,
            deleteClick: (ImageModel) -> Unit,
            position: Int
        ) {
            with(binding) {
                if (position != list.lastIndex) {
                    model.image?.let { binding.ivImage.loadImage(it) }
                    tvUpload.visibility = View.GONE
                    ivCross.visibility = View.VISIBLE
                    ivCross.setOnClickListener { deleteClick(model) }
                    ivImage.setOnClickListener {}
                } else {
                    ivImage.loadImage(model.defaultImg)
                    ivImage.setOnClickListener { addClick(model) }
                    tvUpload.visibility = View.VISIBLE
                    ivCross.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageVH {
        val binding =
            ImageItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageVH(binding)
    }

    override fun onBindViewHolder(holder: ImageVH, position: Int) {
        val item = list[position]
        holder.bind(item, addClick, deleteClick, position)
    }

    override fun getItemCount(): Int = list.size
}