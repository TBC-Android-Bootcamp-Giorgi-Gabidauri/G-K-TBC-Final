package com.gabo.gk.ui.home.products.selling.unsold

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.databinding.FragmentUnsoldProductsBinding
import com.gabo.gk.ui.adapters.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnsoldProductsFragment :
    BaseFragment<FragmentUnsoldProductsBinding>(FragmentUnsoldProductsBinding::inflate) {
    private val viewModel: UnsoldProductsViewModel by viewModels()
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
                    it.msg.isNotEmpty() -> Toast.makeText(
                        requireContext(),
                        it.msg,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    !it.data.isNullOrEmpty() -> productsAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun getSortedProducts() {
        viewModel.getSortedProducts(getString(R.string.sold), false)
    }

    private fun setupAdapters() {
        productsAdapter = ProductsAdapter ({ },{ })
        with(binding) {
            rvProducts.adapter = productsAdapter
            rvProducts.layoutManager = LinearLayoutManager(requireContext())
        }
    }


}