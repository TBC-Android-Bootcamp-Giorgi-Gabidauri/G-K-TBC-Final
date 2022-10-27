package com.gabo.gk.ui.home.user.home

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.domain.useCases.product.GetProductsUseCase
import com.gabo.gk.domain.useCases.product.SearchProductsUseCase
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.modelTransformers.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase
) :
    BaseViewModel<List<ProductModelUi>>() {
    fun getProducts() {
        viewModelScope.launch {
            getData(
                flow = (getProductsUseCase(Unit)),
                mapper = { it.map { domain -> domain.toUi() } },
                success = null, error = null
            )
        }
    }

    fun searchProducts(field: String, query: String) {
        viewModelScope.launch {
            getData(
                flow = (searchProductsUseCase(Pair(field, query))),
                mapper = { it.map { domain -> domain.toUi() } },
                success = null, error = null
            )
        }
    }
}