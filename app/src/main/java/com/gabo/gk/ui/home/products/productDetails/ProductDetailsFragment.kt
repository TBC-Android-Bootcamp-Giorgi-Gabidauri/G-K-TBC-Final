package com.gabo.gk.ui.home.products.productDetails

import android.annotation.SuppressLint
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.loadImage
import com.gabo.gk.databinding.FragmentProductDetailsBinding
import com.gabo.gk.ui.model.product.ProductModelUi
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class ProductDetailsFragment :
    BaseFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate) {
    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun setupView() {
        setInfo(args.productModel)
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            chipCategory.setOnClickListener {
                findNavController().navigate(
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToSortedProductsFragment(
                        args.productModel.productCategory
                    )
                )
            }
            ivArrowBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun setInfo(model: ProductModelUi) {
        with(binding) {
            ivPoster.loadImage(model.photos?.get(0) ?: R.drawable.art)
            tvTitle.text = model.title
            tvDescription.text = model.description
            tvPrice.text =
                if (model.negotiablePrice) getString(R.string.negotiable_price) else "Price: ${model.price}"
            tvSellerName.text = model.sellerName
            chipCategory.text = model.productCategory
            if (model.location != getString(R.string.any_location)) {
                tvLocation.text = model.location
            } else {
                tvLocation.text = getString(R.string.location_not_defined)
            }
            if (model.productCondition != getString(R.string.any)) {
                chipCondition.text = "Condition: ${model.productCondition}"
            } else {
                chipCondition.text = getString(R.string.condition_not_defined)
            }
        }
    }

}