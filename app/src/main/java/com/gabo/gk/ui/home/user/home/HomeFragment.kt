package com.gabo.gk.ui.home.user.home

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.databinding.FragmentHomeBinding
import com.gabo.gk.ui.adapters.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var productsAdapter : ProductsAdapter
    override fun setupView() {
        setupListeners()
        getProducts()
        setupAdapters()
        setupObservers()
    }

    private fun setupListeners() {
        with(binding){
            swipeRl.setOnRefreshListener {
                if(appBar.etSearch.text.toString().isEmpty()){
                    getProducts()
                }else{
                    search(appBar.etSearch.text.toString())
                }
            }
            chipCategories.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_categoriesFragment)
            }
            chipSaved.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_savedItemsFragment)
            }
            chipSelling.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_sellingFragment)
            }
            appBar.etSearch.doOnTextChanged { text, start, before, count ->
                if(appBar.etSearch.text.toString().isEmpty()){
                    getProducts()
                }else{
                    search(appBar.etSearch.text.toString())
                }
            }
        }
    }

    private fun search(query: String) {
        launchStarted {
            withContext(Dispatchers.IO){
                viewModel.searchProducts("title",query)
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.state.collect{
                binding.swipeRl.isRefreshing = it.loading
                when{
                    it.error.isNotEmpty() -> Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT)
                        .show()
//                    it.data.isNotEmpty() -> productsAdapter.submitList(it.data)
                }
                productsAdapter.submitList(it.data)
            }
        }
    }

    private fun setupAdapters() {
        productsAdapter = ProductsAdapter {  }
        with(binding){
            rvProducts.adapter = productsAdapter
            rvProducts.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getProducts() {
        viewLifecycleOwner.launchStarted {
            withContext(Dispatchers.IO){
                viewModel.getProducts()
            }
        }
    }
}