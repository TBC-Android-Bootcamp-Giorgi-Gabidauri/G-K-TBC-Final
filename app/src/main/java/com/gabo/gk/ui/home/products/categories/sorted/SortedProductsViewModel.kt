package com.gabo.gk.ui.home.products.categories.sorted

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.useCases.product.*
import com.gabo.gk.domain.useCases.user.GetUserUseCase
import com.gabo.gk.ui.model.filter.FilterModelUi
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.model.user.UserModelUi
import com.gabo.gk.ui.modelTransformers.toDomain
import com.gabo.gk.ui.modelTransformers.toUi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortedProductsViewModel @Inject constructor(
    private val getFilteredProductsUseCase: GetFilteredProductsUseCase,
    private val saveProductToDbUseCase: SaveProductToDbUseCase,
    private val deleteProductFromDbUseCase: DeleteProductFromDbUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val auth: FirebaseAuth,
    private val getUserUseCase: GetUserUseCase,
    private val getSimilarProductsUseCase: GetSimilarProductsUseCase
) : BaseViewModel<List<ProductModelUi>>() {
    private val _user = MutableStateFlow(UserModelUi())
    val user = _user.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            if (auth.currentUser != null){
                _defaultState.value = _defaultState.value.copy(loading = true)
                when (val result = getUserUseCase(auth.currentUser!!.uid)) {
                    is Resource.Success -> {
                        _defaultState.value =
                            _defaultState.value.copy(loading = false)
                        _user.value = result.data?.toUi() ?: UserModelUi()
                    }
                    is Resource.Error ->
                        _defaultState.value =
                            _defaultState.value.copy( loading = false)
                }
            }
        }
    }
    fun saveProduct(product: ProductModelUi) {
        viewModelScope.launch {
            resetDefaultViewState()
            val result = updateProductUseCase(product.toDomain())
            if (result == "Product updated successfully") {
                saveProductToDbUseCase(product.toDomain())
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
                flow = (getSimilarProductsUseCase(Pair(field, equalsTo))),
                mapper = { it.map { domain -> domain.toUi() } },
                success = null, error = null
            )
        }
    }

    fun getFilteredProducts(model: FilterModelUi) {
        viewModelScope.launch {
            getData(
                flow = (getSimilarProductsUseCase(
                    Pair(model.categoryField, model.categoryEqualsTo)
                )),
                mapper = { it.map { domain -> domain.toUi() } },
                success = null, error = null
            )
            getData(
                flow = (getFilteredProductsUseCase(Pair(model.toDomain(),
                    _defaultState.value.data?.map { it.toDomain() } ?: emptyList()))),
                mapper = { it.map { domain -> domain.toUi() } },
                success = null, error = null
            )
        }
    }
}