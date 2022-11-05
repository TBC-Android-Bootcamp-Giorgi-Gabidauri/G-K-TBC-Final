package com.gabo.gk.ui.home.products.categories.sorted

import android.annotation.SuppressLint
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.constants.FIELD_PRODUCT_CATEGORY
import com.gabo.gk.comon.constants.PRODUCT_GRID_VIEW
import com.gabo.gk.comon.constants.PRODUCT_LIST_VIEW
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.snackBar
import com.gabo.gk.comon.extensions.txt
import com.gabo.gk.databinding.FragmentSortedProductsBinding
import com.gabo.gk.ui.adapters.ProductsAdapter
import com.gabo.gk.ui.model.filter.FilterModelUi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SortedProductsFragment :
    BaseFragment<FragmentSortedProductsBinding>(FragmentSortedProductsBinding::inflate) {
    private val viewModel: SortedProductsViewModel by viewModels()
    private val args: SortedProductsFragmentArgs by navArgs()
    private lateinit var productsAdapter: ProductsAdapter
    private var viewControl = true
    @Inject lateinit var auth: FirebaseAuth
    override fun setupView() {
        getSortedProducts()
        setupAppBar()
        setupAdapters()
        setupObservers()
        setupListeners()
        setupDropDownAdapters()
    }

    private fun setupDropDownAdapters() {
        val showFirst = resources.getStringArray(R.array.ShowFirst)
        val languagesAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item_view, showFirst)
        binding.navLayoutFilter.autoCTvShowFirst.setAdapter(languagesAdapter)
    }

    private fun setupListeners() {
        with(binding) {
            swipeRl.setOnRefreshListener {
                getSortedProducts()
            }
            appBar.ivArrowBack.setOnClickListener { findNavController().navigateUp() }
            tvFilter.setOnClickListener { drawerLayout.openDrawer(GravityCompat.END) }
            with(navLayoutFilter) {
                ivArrowClose.setOnClickListener { drawerLayout.closeDrawer(GravityCompat.END) }
                ivView.setOnClickListener {
                    viewControl = if (viewControl) {
                        ivView.setImageResource(R.drawable.ic_list_view)
                        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(),2)
                        productsAdapter.changeView(PRODUCT_GRID_VIEW)
                        false
                    } else {
                        ivView.setImageResource(R.drawable.ic_grid_view)
                        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
                        productsAdapter.changeView(PRODUCT_LIST_VIEW)
                        true
                    }
                }
                chipNegotiablePrice.setOnCheckedChangeListener { compoundButton, b ->
                    etPriceFrom.isVisible = !chipNegotiablePrice.isChecked
                    etPriceTill.isVisible = !chipNegotiablePrice.isChecked
                }
                btnAdjust.setOnClickListener {
                    filter()
                    drawerLayout.closeDrawer(GravityCompat.END)
                }
            }
        }
    }

    private fun filter() {
        with(binding.navLayoutFilter) {
            val model = FilterModelUi(
                autoCTvShowFirst.txt(),
                type = when {
                    chipTypeOnlySeller.isChecked -> chipTypeOnlySeller.txt()
                    chipTypeOnlyShop.isChecked -> chipTypeOnlyShop.txt()
                    else -> chipTypeAll.txt()
                },
                status = when {
                    chipStatusNew.isChecked -> chipStatusNew.txt()
                    chipStatusAlmostNew.isChecked -> chipStatusAlmostNew.txt()
                    chipStatusSecondHand.isChecked -> chipStatusSecondHand.txt()
                    chipStatusDetails.isChecked -> chipStatusDetails.txt()
                    else -> chipStatusAll.txt()
                },
                anyPrice = chipAnyPrice.isChecked,
                negotiablePrice = chipNegotiablePrice.isChecked,
                priceFrom = etPriceFrom.txt().toIntOrNull() ?: 0,
                priceTill = etPriceTill.txt().toIntOrNull() ?: 999999999,
                location = when {
                    chipLocationTbilisi.isChecked -> chipLocationTbilisi.txt()
                    chipLocationBatumi.isChecked -> chipLocationBatumi.txt()
                    chipLocationGori.isChecked -> chipLocationGori.txt()
                    chipLocationKutaisi.isChecked -> chipLocationKutaisi.txt()
                    chipLocationPoti.isChecked -> chipLocationPoti.txt()
                    else -> chipLocationAll.txt()
                },
                categoryField = FIELD_PRODUCT_CATEGORY,
                categoryEqualsTo = args.category
            )
            viewModel.getFilteredProducts(model)
        }
    }

    private fun setupAppBar() {
        with(binding) {
            appBar.ivArrowBack.visibility = View.GONE
            appBar.tvTitle.text = args.category
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.defaultState.collect {
                binding.swipeRl.isRefreshing = it.loading
                if (it.msg.isNotEmpty()) binding.root.snackBar(it.msg)
                it.data?.let { list -> productsAdapter.submitList(list) }
            }
        }
    }

    private fun getSortedProducts() {
        viewModel.getSortedProducts(getString(R.string.productCategory), args.category)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupAdapters() {
        productsAdapter = ProductsAdapter(auth.currentUser!!.uid,itemClick =  {
            findNavController().navigate(
                SortedProductsFragmentDirections.actionSortedProductsFragmentToProductDetailsFragment(
                    it
                )
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
            rvProducts.adapter = productsAdapter
            rvProducts.layoutManager = LinearLayoutManager(requireContext())
        }
    }

}