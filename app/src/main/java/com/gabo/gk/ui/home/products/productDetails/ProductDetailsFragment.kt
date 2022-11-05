package com.gabo.gk.ui.home.products.productDetails

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.loadImage
import com.gabo.gk.comon.extensions.snackBar
import com.gabo.gk.databinding.FragmentProductDetailsBinding
import com.gabo.gk.ui.adapters.ProductsAdapter
import com.gabo.gk.ui.model.product.ProductModelUi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class ProductDetailsFragment :
    BaseFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate) {
    private val args: ProductDetailsFragmentArgs by navArgs()
    private val viewModel: ProductDetailsViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    @Inject
    lateinit var auth: FirebaseAuth
    override fun setupView() {
        getSortedProducts()
        setInfo(args.productModel)
        setupListeners()
        setupObservers()
        setupAdapters()
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.defaultState.collect {
                if (it.msg.isNotEmpty()) binding.root.snackBar(it.msg)
                it.data?.let { list -> productsAdapter.submitList(list) }
            }
        }
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
            ivHeart.setOnClickListener {
                val product = args.productModel
                if (product.isSaved.contains(auth.currentUser!!.uid)) {
                    val list = args.productModel.isSaved.toMutableList()
                    list.remove(auth.currentUser!!.uid)
                    args.productModel.isSaved = list
                    viewModel.deleteProduct(args.productModel)
                    ivHeart.setImageResource(R.drawable.ic_heart)
                } else {
                    val list = args.productModel.isSaved.toMutableList()
                    list.add(auth.currentUser!!.uid)
                    args.productModel.isSaved = list
                    viewModel.saveProduct(args.productModel)
                    ivHeart.setImageResource(R.drawable.ic_heart_filled)
                }
            }

        }
    }

    private fun getSortedProducts() {
        viewModel.getSortedProducts(
            getString(R.string.productCategory),
            args.productModel.productCategory
        )
    }

    private fun setInfo(model: ProductModelUi) {
        with(binding) {
            ivPoster.loadImage(model.photos?.get(0) ?: R.drawable.art)
            tvTitle.text = model.title
            tvDescription.text = model.description
            tvPrice.text =
                if (model.negotiablePrice) getString(R.string.negotiable_price) else "Price: ${model.price} $"
            tvSellerName.text = model.sellerName
            chipCategory.text = model.productCategory
            tvSold.visibility = if (model.sold) View.VISIBLE else View.GONE
            ivHeart.visibility = if (model.sold) View.GONE else View.VISIBLE
            btnBuyNow.visibility = if (model.sold) View.GONE else View.VISIBLE
            tvLocation.text =
                if (model.location != getString(R.string.any_location)) model.location else getString(
                    R.string.location_not_defined
                )

            chipCondition.text = if (model.productCondition != getString(R.string.any))
                "Condition: ${model.productCondition}" else getString(R.string.condition_not_defined)

            if (model.uid == auth.currentUser!!.uid) {
                ivHeart.visibility = View.GONE
                btnTextSeller.text = getString(R.string.mark_as_sold)
                btnTextSeller.setOnClickListener {
                    viewModel.markAsSold(args.productModel)
                }
                btnBuyNow.text = getString(R.string.delete_product)
                btnBuyNow.setOnClickListener {
                    viewModel.deleteProductPermanently(args.productModel)
                    findNavController().navigateUp()
                }
            } else {
                if (!model.sold){
                    ivHeart.visibility = View.VISIBLE
                }
                btnTextSeller.text = getString(R.string.text_seller)
                btnBuyNow.text = getString(R.string.buy_now)
                btnBuyNow.setOnClickListener {
                    viewModel.buyProduct(args.productModel)
                }
            }
            ivHeart.setImageResource(
                if (model.isSaved.contains(auth.currentUser!!.uid)) R.drawable.ic_heart_filled else R.drawable.ic_heart
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupAdapters() {
        productsAdapter = ProductsAdapter(auth.currentUser!!.uid, itemClick = {
            findNavController().navigate(
                ProductDetailsFragmentDirections.actionProductDetailsFragmentSelf(it)
            )
        }, heartClick = {
            if (it.isSaved.contains(auth.currentUser!!.uid)) {
                it.isSaved.remove(auth.currentUser!!.uid)
                viewModel.deleteProduct(it)
                productsAdapter.notifyDataSetChanged()
            } else {
                it.isSaved.add(auth.currentUser!!.uid)
                viewModel.saveProduct(it)
                productsAdapter.notifyDataSetChanged()
            }
        })
        with(binding) {
            rvSimilarProducts.adapter = productsAdapter
            rvSimilarProducts.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}