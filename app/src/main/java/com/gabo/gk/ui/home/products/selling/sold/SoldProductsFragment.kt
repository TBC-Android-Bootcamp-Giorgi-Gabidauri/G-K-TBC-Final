package com.gabo.gk.ui.home.products.selling.sold

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.databinding.FragmentSoldProductsBinding
import com.gabo.gk.ui.adapters.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SoldProductsFragment :
    BaseFragment<FragmentSoldProductsBinding>(FragmentSoldProductsBinding::inflate) {
    private val viewModel: SoldProductsViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter
    override fun setupView() {
        getSortedProducts()
        setupAdapters()
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            swipeRl.setOnRefreshListener {
                getSortedProducts()
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.defaultState.collect {
                binding.swipeRl.isRefreshing = it.loading
                when {
                    it.error.isNotEmpty() -> Toast.makeText(
                        requireContext(),
                        it.error,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    !it.data.isNullOrEmpty() -> productsAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun getSortedProducts() {
        viewModel.getSortedProducts(getString(R.string.sold), true)
    }

    private fun setupAdapters() {
        productsAdapter = ProductsAdapter { }
        with(binding) {
            rvProducts.adapter = productsAdapter
            rvProducts.layoutManager = LinearLayoutManager(requireContext())
        }
    }


}