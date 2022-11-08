package com.gabo.gk.ui.home.products.saved

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.useCases.product.DeleteProductFromDbUseCase
import com.gabo.gk.domain.useCases.product.GetFilteredProductsUseCase
import com.gabo.gk.domain.useCases.product.GetSavedProductsScenario
import com.gabo.gk.domain.useCases.product.UpdateProductUseCase
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
class SavedItemsViewModel @Inject constructor(
    private val getSavedProductsScenario: GetSavedProductsScenario,
    private var getFilteredProductsUseCase: GetFilteredProductsUseCase,
    private val deleteProductFromDbUseCase: DeleteProductFromDbUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val auth: FirebaseAuth,
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel<List<ProductModelUi>>() {
    private val _user = MutableStateFlow(UserModelUi())
    val user = _user.asStateFlow()
    init {
        getSavedProducts()
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

    fun getSavedProducts() {
        viewModelScope.launch {
            _defaultState.value = _defaultState.value.copy(loading = true)
            getSavedProductsScenario(Unit).collect {
                _defaultState.value =
                    _defaultState.value.copy(data = it.map { domain -> domain.toUi() }, loading = false)
            }
        }
    }

    fun getFilteredProducts(model: FilterModelUi) {
        viewModelScope.launch {
            getSavedProductsScenario(Unit).collect {
                getData(
                    flow = (getFilteredProductsUseCase(Pair(model.toDomain(), it))),
                    mapper = { list -> list.map { productModelDomain -> productModelDomain.toUi() } },
                    success = null, error = null
                )
            }
        }
    }
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
}