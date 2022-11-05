package com.gabo.gk.ui.home.products.purchases

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.snackBar
import com.gabo.gk.databinding.FragmentPurchasesBinding
import com.gabo.gk.ui.adapters.ProductsAdapter
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PurchasesFragment :
    BaseFragment<FragmentPurchasesBinding>(FragmentPurchasesBinding::inflate) {
    private val viewModel: PurchasesViewModel by viewModels()
    private lateinit var purchasesAdapter: ProductsAdapter

    @Inject
    lateinit var auth: FirebaseAuth
    override fun setupView() {
        setupAppBar()
        setupObservers()
        setupAdapters()
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            swipeRl.setOnRefreshListener {
                viewModel.getPurchases()
            }
        }
    }

    private fun setupAdapters() {
        purchasesAdapter = ProductsAdapter(auth.currentUser!!.uid, {
            findNavController().navigate(
                PurchasesFragmentDirections.actionPurchasesFragmentToProductDetailsFragment(
                    it
                )
            )
        }, {})
        with(binding) {
            rvPurchases.adapter = purchasesAdapter
            rvPurchases.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.defaultState.collect {
                binding.swipeRl.isRefreshing = it.loading
                when {
                    it.msg.isNotEmpty() -> binding.root.snackBar(it.msg)
                    !it.data.isNullOrEmpty() -> purchasesAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun setupAppBar() {
        with(binding.appBar) {
            ivArrowBack.visibility = View.GONE
            tvTitle.text = getString(R.string.purchases)
        }
    }

}