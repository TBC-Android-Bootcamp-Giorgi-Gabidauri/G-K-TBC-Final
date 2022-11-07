package com.gabo.gk.ui.home.products.selling.active

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.databinding.FragmentActiveSellingBinding
import com.gabo.gk.ui.adapters.ProductsAdapter
import com.gabo.gk.ui.home.products.selling.SellingFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActiveSellingFragment :
    BaseFragment<FragmentActiveSellingBinding>(FragmentActiveSellingBinding::inflate) {
    private val viewModel: ActiveSellingViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    @Inject
    lateinit var auth: FirebaseAuth
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
            btnAdd.setOnClickListener {
                findNavController().navigate(R.id.addSellingProductFragment)
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
                    ).show()
                    !it.data.isNullOrEmpty() -> productsAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun getSortedProducts() {
        viewModel.getSortedProducts(getString(R.string.uid))
    }

    private fun setupAdapters() {
        productsAdapter = ProductsAdapter(auth.currentUser!!.uid, itemClick = {
            findNavController().navigate(SellingFragmentDirections.actionSellingFragmentToProductDetailsFragment(it))
        }, {})
        with(binding) {
            rvProducts.adapter = productsAdapter
            rvProducts.layoutManager = LinearLayoutManager(requireContext())
        }
    }

}