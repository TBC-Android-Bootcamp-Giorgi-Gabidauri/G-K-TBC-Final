package com.gabo.gk.ui.home.products.selling

import androidx.navigation.fragment.findNavController
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.databinding.FragmentSellingBinding
import com.gabo.gk.ui.adapters.SellingPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SellingFragment : BaseFragment<FragmentSellingBinding>(FragmentSellingBinding::inflate) {
    override fun setupView() {
        setupTabLayout()
        setupListeners()
    }

    private fun setupListeners() {
        with(binding){
            appBar.ivArrowBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun setupTabLayout() {
        with(binding){
            vpSelling.adapter = SellingPagerAdapter(requireActivity())
            TabLayoutMediator(tlSelling,vpSelling){tab, index ->
                tab.text = when(index){
                    0-> "Active"
                    1-> "Sold"
                    else -> "Unsold"
                }
            }.attach()
        }
    }

}