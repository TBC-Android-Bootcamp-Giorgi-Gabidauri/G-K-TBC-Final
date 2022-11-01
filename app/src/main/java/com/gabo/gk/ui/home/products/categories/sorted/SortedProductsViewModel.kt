package com.gabo.gk.ui.home.products.categories.sorted

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.domain.useCases.product.*
import com.gabo.gk.ui.model.filter.FilterModelUi
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.modelTransformers.toDomain
import com.gabo.gk.ui.modelTransformers.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortedProductsViewModel @Inject constructor(
    private val getSortedProductsUseCase: GetSortedProductsUseCase,
    private val getFilteredProductsUseCase: GetFilteredProductsUseCase,
    private val saveProductUseCase: SaveProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) : BaseViewModel<List<ProductModelUi>>() {
    fun updateProduct(newProduct: ProductModelUi) {
        viewModelScope.launch {
            updateProductUseCase(newProduct.toDomain())
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

    fun getSortedProducts(field: String, equalsTo: String) {
        viewModelScope.launch {
            getData(
                flow = (getSortedProductsUseCase(Pair(field, equalsTo))),
                mapper = { it.map { domain -> domain.toUi() } },
                success = null, error = null
            )
        }
    }

    fun getFilteredProducts(model: FilterModelUi) {
        viewModelScope.launch {
            getData(
                flow = (getSortedProductsUseCase(
                    Pair(
                        model.categoryField,
                        model.categoryEqualsTo
                    )
                )),
                mapper = { it.map { domain -> domain.toUi() } },
                success = null, error = null
            )
            getData(
                flow = (getFilteredProductsUseCase(
                    Pair(
                        model.toDomain(),
                        _defaultState.value.data?.map { it.toDomain() } ?: emptyList()
                    )
                )),
                mapper = {

                    it.map { domain -> domain.toUi() }
                },
                success = null, error = null
            )
        }
    }
}