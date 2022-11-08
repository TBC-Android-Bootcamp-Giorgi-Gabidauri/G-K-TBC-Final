package com.gabo.gk.ui.home.products.productDetails

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.useCases.product.*
import com.gabo.gk.domain.useCases.user.GetUserUseCase
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.model.user.UserModelUi
import com.gabo.gk.ui.modelTransformers.toDomain
import com.gabo.gk.ui.modelTransformers.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val updateProductUseCase: UpdateProductUseCase,
    private val saveProductToDbUseCase: SaveProductToDbUseCase,
    private val deleteProductFromDbUseCase: DeleteProductFromDbUseCase,
    private val buyProductScenario: BuyProductScenario,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val markProductAsSoldUseCase: MarkProductAsSoldUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getSimilarProductsUseCase: GetSimilarProductsUseCase
) : BaseViewModel<List<ProductModelUi>>() {
    private val _user: MutableStateFlow<UserModelUi> = MutableStateFlow(UserModelUi())
    val user = _user.asStateFlow()

    fun markAsSold(product: ProductModelUi) {
        viewModelScope.launch {
            val result = markProductAsSoldUseCase(product.toDomain())
            _defaultState.value = _defaultState.value.copy(msg = result)
        }
    }

    fun getUser(uid: String) {
        viewModelScope.launch {
            when (val result = getUserUseCase(uid)) {
                is Resource.Success -> {
                    _user.value = result.data!!.toUi()
                }
                is Resource.Error -> _defaultState.value =
                    _defaultState.value.copy(msg = result.errorMsg!!)
            }
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

    fun getSimilarProducts(field: String, equalsTo: String) {
        viewModelScope.launch {
            getData(
                flow = (getSimilarProductsUseCase(Pair(field, equalsTo))),
                mapper = { it.map { domain -> domain.toUi() } },
                success = null, error = null
            )
        }
    }
}