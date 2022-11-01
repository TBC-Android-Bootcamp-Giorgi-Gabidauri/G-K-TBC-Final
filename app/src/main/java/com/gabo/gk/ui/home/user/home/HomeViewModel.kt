package com.gabo.gk.ui.home.user.home

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.domain.useCases.product.*
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.modelTransformers.toDomain
import com.gabo.gk.ui.modelTransformers.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
    private val saveProductUseCase: SaveProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) : BaseViewModel<List<ProductModelUi>>() {

    fun updateProduct(newProduct: ProductModelUi) {
        viewModelScope.launch {
            updateProductUseCase(newProduct.toDomain()).collect {
                it?.let { _defaultState.value = _defaultState.value.copy(msg = it) }
            }
        }.invokeOnCompletion {
//            getProducts()
        }
    }

    fun saveProduct(product: ProductModelUi) {
        viewModelScope.launch {
            saveProductUseCase(product.toDomain())
        }
    }

    fun deleteProduct(id: String) {
        viewModelScope.launch {
            deleteProductUseCase(id)
        }
    }

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