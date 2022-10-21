package com.gabo.gk.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gabo.gk.ui.home.products.selling.active.ActiveSellingFragment
import com.gabo.gk.ui.home.products.selling.sold.SoldProductsFragment
import com.gabo.gk.ui.home.products.selling.unsold.UnsoldProductsFragment

class SellingPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return  when(position){
            0 -> ActiveSellingFragment()
            1 -> SoldProductsFragment()
            else -> UnsoldProductsFragment()
        }
    }
}