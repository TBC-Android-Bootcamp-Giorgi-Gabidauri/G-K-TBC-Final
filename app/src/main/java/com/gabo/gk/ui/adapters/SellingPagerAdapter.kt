package com.gabo.gk.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gabo.gk.ui.home.products.selling.active.ActiveSellingFragment
import com.gabo.gk.ui.home.products.selling.sold.SoldProductsFragment

class SellingPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return  when(position){
            0 -> ActiveSellingFragment()
            else -> SoldProductsFragment()
        }
    }
}