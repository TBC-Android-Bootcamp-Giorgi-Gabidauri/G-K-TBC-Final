package com.gabo.gk.ui.home.products.selling

import android.view.View
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
        setupAppBar()
    }


    private fun setupAppBar() {
        with(binding) {
            appBar.ivArrowBack.visibility = View.GONE
            appBar.tvTitle.text = getString(R.string.Selling)
        }
    }

    private fun setupTabLayout() {
        with(binding) {
            vpSelling.adapter = SellingPagerAdapter(requireActivity())
            TabLayoutMediator(tlSelling, vpSelling) { tab, index ->
                tab.text = when (index) {
                    0 -> ACTIVE
                    1 -> SOLD
                    else -> UNSOLD
                }
            }.attach()
        }
    }

    companion object {
        const val ACTIVE = "Active"
        const val SOLD = "Sold"
        const val UNSOLD = "Unsold"
    }
}