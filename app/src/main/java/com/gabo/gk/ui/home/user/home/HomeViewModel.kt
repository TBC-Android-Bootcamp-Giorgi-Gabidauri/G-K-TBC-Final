package com.gabo.gk.ui.home.user.home

import androidx.lifecycle.ViewModel
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.useCases.product.GetProductsUseCase
import com.gabo.gk.domain.useCases.product.SearchProductsUseCase
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.modelTransformers.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase
) :
    ViewModel() {
    private val _state = MutableStateFlow(ViewState())
    val state = _state.asStateFlow()

    suspend fun getProducts() {
        resetViewState()
        _state.value = _state.value.copy(loading = true)
        getProductsUseCase(Unit).collect {
            withContext(Dispatchers.Main) {
                when (it) {
                    is Resource.Success -> _state.value =
                        _state.value.copy(
                            loading = false,
                            data = it.data?.map { domain -> domain.toUi() } ?: emptyList()
                        )
                    is Resource.Error -> _state.value = _state.value.copy(
                        loading = false,
                        error = it.errorMsg ?: "server error"
                    )
                }
            }
        }
    }

    suspend fun searchProducts(field: String, query: String) {
        resetViewState()
        _state.value = _state.value.copy(loading = true)
        searchProductsUseCase(Pair(field, query)).collect {
            withContext(Dispatchers.Main) {
                when (it) {
                    is Resource.Success -> _state.value =
                        _state.value.copy(
                            loading = false,
                            data = it.data?.map { domain -> domain.toUi() } ?: emptyList()
                        )
                    is Resource.Error -> _state.value = _state.value.copy(
                        loading = false,
                        error = it.errorMsg ?: "server error"
                    )
                }
            }
        }
    }

    private fun resetViewState() {
        _state.value = _state.value.copy(error = "", data = emptyList(), loading = false)
    }

    data class ViewState(
        val error: String = "",
        val data: List<ProductModelUi> = emptyList(),
        val loading: Boolean = false
    )
}