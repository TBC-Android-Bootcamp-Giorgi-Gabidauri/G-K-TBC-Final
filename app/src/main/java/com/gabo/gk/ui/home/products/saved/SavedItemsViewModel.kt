package com.gabo.gk.ui.home.products.saved

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
class SavedItemsViewModel @Inject constructor(
    private val getSavedProductsUseCase: GetSavedProductsUseCase,
    private var getFilteredProductsUseCase: GetFilteredProductsUseCase,
    private val saveProductUseCase: SaveProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) :
    BaseViewModel<List<ProductModelUi>>() {
    init {
        getSavedProducts()
    }
    fun updateProduct(newProduct: ProductModelUi){
        viewModelScope.launch {
            updateProductUseCase(newProduct.toDomain())
        }
    }

    fun saveProduct(product: ProductModelUi){
        viewModelScope.launch {
            saveProductUseCase(product.toDomain())
        }
    }
    fun deleteProduct(id:String){
        viewModelScope.launch {
            deleteProductUseCase(id)
        }
    }

    fun getSavedProducts() {
        viewModelScope.launch {
            getSavedProductsUseCase(Unit).collect {
                _defaultState.value =
                    _defaultState.value.copy(data = it.map { domain -> domain.toUi() })
            }
        }
    }

    fun getFilteredProducts(model: FilterModelUi) {
        viewModelScope.launch {
            getSavedProductsUseCase(Unit).collect {
                getData(
                    flow = (getFilteredProductsUseCase(Pair(model.toDomain(), it))),
                    mapper = { list -> list.map { productModelDomain -> productModelDomain.toUi() } },
                    success = null, error = null
                )
            }
        }
    }
}