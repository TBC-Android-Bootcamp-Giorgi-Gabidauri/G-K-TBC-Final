package com.gabo.gk.ui.home.user.home

import androidx.fragment.app.viewModels
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()

    override fun setupView() {

    }
}