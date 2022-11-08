package com.gabo.gk.ui.home.user.home

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.useCases.product.*
import com.gabo.gk.domain.useCases.user.GetUserUseCase
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
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
    private val saveProductToDbUseCase: SaveProductToDbUseCase,
    private val deleteProductFromDbUseCase: DeleteProductFromDbUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val auth: FirebaseAuth,
    private val getUserUseCase: GetUserUseCase
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