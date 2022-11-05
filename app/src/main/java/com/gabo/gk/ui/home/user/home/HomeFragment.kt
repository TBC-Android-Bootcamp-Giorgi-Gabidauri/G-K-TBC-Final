package com.gabo.gk.ui.home.user.home

import android.annotation.SuppressLint
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.constants.FIELD_TITLE
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.snackBar
import com.gabo.gk.comon.extensions.txt
import com.gabo.gk.databinding.FragmentHomeBinding
import com.gabo.gk.ui.adapters.ProductsAdapter
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter
    @Inject
    lateinit var auth: FirebaseAuth
    override fun setupView() {
        setupAdapters()
        setupListeners()
        getProducts()
        setupObservers()
    }

    private fun setupListeners() {
        with(binding) {
            swipeRl.setOnRefreshListener {
                if (appBar.etSearch.txt().isEmpty()) {
                    getProducts()
                } else {
                    search(appBar.etSearch.txt())
                }
            }
            tvCategories.setOnClickListener {
                findNavController().navigate(R.id.categoriesFragment)
            }
            tvSaved.setOnClickListener {
                findNavController().navigate(R.id.savedItemsFragment)
            }
            tvSelling.setOnClickListener {
                findNavController().navigate(R.id.sellingFragment)
            }
            appBar.etSearch.doOnTextChanged { text, start, before, count ->
                if (appBar.etSearch.txt().isEmpty()) {
                    getProducts()
                } else {
                    search(appBar.etSearch.txt())
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
                    it.msg.isNotEmpty() -> binding.root.snackBar(it.msg)
                    !it.data.isNullOrEmpty() -> productsAdapter.submitList(it.data)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupAdapters() {
        productsAdapter = ProductsAdapter(auth.currentUser!!.uid, itemClick = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it)
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

    private fun getProducts() {
        viewModel.getProducts()
    }
}