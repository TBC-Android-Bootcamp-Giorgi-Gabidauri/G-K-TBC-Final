package com.gabo.gk.ui.home.products.selling.active

import androidx.navigation.fragment.findNavController
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.databinding.FragmentActiveSellingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActiveSellingFragment :
    BaseFragment<FragmentActiveSellingBinding>(FragmentActiveSellingBinding::inflate) {
    override fun setupView() {
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_sellingFragment_to_addSellingProductFragment)
        }
    }
}