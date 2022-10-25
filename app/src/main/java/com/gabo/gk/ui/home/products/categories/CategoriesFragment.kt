package com.gabo.gk.ui.home.products.categories

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.comon.util.CategoriesListProvider.CategoriesList
import com.gabo.gk.databinding.FragmentCategoriesBinding
import com.gabo.gk.ui.adapters.CategoriesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment :
    BaseFragment<FragmentCategoriesBinding>(FragmentCategoriesBinding::inflate) {
    private lateinit var adapter: CategoriesAdapter
    override fun setupView() {
        setupAdapters()
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            appBar.ivArrowBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun setupAdapters() {
        adapter = CategoriesAdapter { navigate(it.title) }
        binding.rvCategories.adapter = adapter
        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter.submitList(CategoriesList)
    }

    private fun navigate(category: String) {
        findNavController().navigate(
            CategoriesFragmentDirections.actionCategoriesFragmentToSortedProductsFragment(
                category
            )
        )
    }

}