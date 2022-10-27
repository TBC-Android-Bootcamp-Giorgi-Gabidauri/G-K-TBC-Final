package com.gabo.gk.ui.home.products.selling

import android.view.View
import androidx.core.view.GravityCompat
import com.gabo.gk.R
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
        setupAppBar()
    }
    private fun setupAppBar() {
        with(binding){
            appBar.ivArrowBack.visibility = View.GONE
            appBar.tvTitle.text = getString(R.string.Selling)
        }
    }
    private fun setupListeners() {
        with(binding){
            tvFilter.setOnClickListener { drawerLayout.openDrawer(GravityCompat.END) }
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