package com.gabo.gk.ui.home.products.selling.sold

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.constants.TAG
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.databinding.FragmentSoldProductsBinding
import com.gabo.gk.ui.adapters.ProductsAdapter
import com.gabo.gk.ui.home.products.selling.SellingFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SoldProductsFragment :
    BaseFragment<FragmentSoldProductsBinding>(FragmentSoldProductsBinding::inflate) {
    private val viewModel: SoldProductsViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    @Inject
    lateinit var auth: FirebaseAuth
    override fun setupView() {
        setupAdapters()
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            swipeRl.setOnRefreshListener {
                viewModel.getSoldProducts()
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.defaultState.collect {
                binding.swipeRl.isRefreshing = it.loading
                when {
                    it.msg.isNotEmpty() -> d(TAG, it.msg)
                    !it.data.isNullOrEmpty() -> productsAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun setupAdapters() {
        productsAdapter = ProductsAdapter(auth.currentUser!!.uid, itemClick = {
            findNavController().navigate(
                SellingFragmentDirections.actionSellingFragmentToProductDetailsFragment(
                    it
                )
            )
        }, {})
        with(binding) {
            rvProducts.adapter = productsAdapter
            rvProducts.layoutManager = LinearLayoutManager(requireContext())
        }
    }


}