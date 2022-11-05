package com.gabo.gk.ui.home.products.productDetails

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
class ProductDetailsViewModel @Inject constructor(
    private val updateProductUseCase: UpdateProductUseCase,
    private val saveProductUseCase: SaveProductUseCase,
    private val deleteProductFromDbUseCase: DeleteProductFromDbUseCase,
    private val buyProductScenario: BuyProductScenario,
    private val getSortedProductsUseCase: GetSortedProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val markProductAsSoldUseCase: MarkProductAsSoldUseCase
) : BaseViewModel<List<ProductModelUi>>() {
    fun markAsSold(product: ProductModelUi) {
        viewModelScope.launch {
            val result = markProductAsSoldUseCase(product.toDomain())
            _defaultState.value = _defaultState.value.copy(msg = result)
        }
    }

    fun deleteProductPermanently(product: ProductModelUi) {
        viewModelScope.launch {
            val result = deleteProductUseCase(product.toDomain())
            _defaultState.value = _defaultState.value.copy(msg = result)
        }
    }

    fun buyProduct(product: ProductModelUi) {
        viewModelScope.launch {
            val msg = buyProductScenario(product.toDomain())
            _defaultState.value = _defaultState.value.copy(msg = msg)
        }
    }

    fun saveProduct(product: ProductModelUi) {
        viewModelScope.launch {
            resetDefaultViewState()
            val result = updateProductUseCase(product.toDomain())
            if (result == "Product updated successfully") {
                saveProductUseCase(product.toDomain())
            }
            _defaultState.value = _defaultState.value.copy(msg = result)

        }
    }

    fun deleteProduct(product: ProductModelUi) {
        viewModelScope.launch {
            resetDefaultViewState()
            val result = updateProductUseCase(product.toDomain())
            if (result == "Product updated successfully") {
                deleteProductFromDbUseCase(product.id)
            }
            _defaultState.value = _defaultState.value.copy(msg = result)
        }

    }

    fun getSortedProducts(field: String, equalsTo: String) {
        viewModelScope.launch {
            getData(
                flow = (getSortedProductsUseCase(Pair(field, equalsTo))),
                mapper = { it.map { domain -> domain.toUi() } },
                success = null, error = null
            )
        }
    }
}