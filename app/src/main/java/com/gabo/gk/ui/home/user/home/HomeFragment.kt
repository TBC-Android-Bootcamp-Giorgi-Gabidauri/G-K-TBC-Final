package com.gabo.gk.ui.home.user.home

import androidx.core.widget.doOnTextChanged
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun setupView() {

        binding.appBar.etSearch.doOnTextChanged { text, start, before, count ->
            binding.tvHome.text = binding.appBar.etSearch.text.toString()
        }
    }

}