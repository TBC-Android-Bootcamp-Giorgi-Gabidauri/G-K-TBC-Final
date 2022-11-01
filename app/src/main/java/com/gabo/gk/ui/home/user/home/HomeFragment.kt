package com.gabo.gk.ui.home.user.home

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.constants.FIELD_TITLE
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.databinding.FragmentHomeBinding
import com.gabo.gk.ui.adapters.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter
    override fun setupView() {
        setupAdapters()
        setupListeners()
        getProducts()
        setupObservers()
    }

    private fun setupListeners() {
        with(binding) {
            swipeRl.setOnRefreshListener {
                if (appBar.etSearch.text.toString().isEmpty()) {
                    getProducts()
                } else {
                    search(appBar.etSearch.text.toString())
                }
            }
            chipCategories.setOnClickListener {
                findNavController().navigate(R.id.categoriesFragment)
            }
            chipSaved.setOnClickListener {
                findNavController().navigate(R.id.savedItemsFragment)
            }
            chipSelling.setOnClickListener {
                findNavController().navigate(R.id.sellingFragment)
            }
            appBar.etSearch.doOnTextChanged { text, start, before, count ->
                if (appBar.etSearch.text.toString().isEmpty()) {
                    getProducts()
                } else {
                    search(appBar.etSearch.text.toString())
                }
            }
        }
    }

    private fun search(query: String) {
        viewModel.searchProducts(FIELD_TITLE, query)
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.defaultState.collect {
                if (binding.swipeRl.isRefreshing) {
                    binding.swipeRl.isRefreshing = false
                }
                when {
                    it.error.isNotEmpty() -> Toast.makeText(
                        requireContext(), it.error, Toast.LENGTH_SHORT
                    ).show()
                }
                if (it.data != null) {
                    productsAdapter.submitList(it.data)
                } else {
                    productsAdapter.submitList(emptyList())
                }
            }
        }
    }

    private fun setupAdapters() {
        productsAdapter = ProductsAdapter(itemClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it)
            )
        }, heartClick = {
            if (it.isSaved.contains(it.uid)) {
                viewModel.deleteProduct(it.id)
                it.isSaved = it.isSaved.toMutableList()
                (it.isSaved as MutableList<String>).remove(it.uid)
                it.isSaved = it.isSaved.toList()
                viewModel.updateProduct(it)
                productsAdapter.notifyDataSetChanged()
            } else {
                it.isSaved = it.isSaved.toMutableList()
                (it.isSaved as MutableList<String>).add(it.uid)
                it.isSaved = it.isSaved.toList()
                viewModel.saveProduct(it)
                viewModel.updateProduct(it)
                productsAdapter.notifyDataSetChanged()
            }
        })
        with(binding) {
            rvProducts.adapter = productsAdapter
            rvProducts.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getProducts() {
        viewModel.getProducts()
    }
}