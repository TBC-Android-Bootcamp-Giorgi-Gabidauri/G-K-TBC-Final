package com.gabo.gk.ui.home.products.categories.sorted

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.databinding.FragmentSortedProductsBinding
import com.gabo.gk.ui.adapters.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortedProductsFragment :
    BaseFragment<FragmentSortedProductsBinding>(FragmentSortedProductsBinding::inflate) {
    private val viewModel: SortedProductsViewModel by viewModels()
    private val args: SortedProductsFragmentArgs by navArgs()
    private lateinit var productsAdapter: ProductsAdapter
    override fun setupView() {
        getSortedProducts()
        setupAppBar()
        setupAdapters()
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            swipeRl.setOnRefreshListener {
                getSortedProducts()
            }
            appBar.ivArrowBack.setOnClickListener { findNavController().navigateUp() }
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
            viewModel.state.collect {
                binding.swipeRl.isRefreshing = it.loading
                when {
                    it.error.isNotEmpty() -> Toast.makeText(
                        requireContext(),
                        it.error,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    it.data.isNotEmpty() -> productsAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun getSortedProducts() {
        viewModel.getSortedProducts(getString(R.string.productCategory), args.category)
    }

    private fun setupAdapters() {
        productsAdapter = ProductsAdapter { }
        with(binding) {
            rvProducts.adapter = productsAdapter
            rvProducts.layoutManager = LinearLayoutManager(requireContext())
        }
    }

}