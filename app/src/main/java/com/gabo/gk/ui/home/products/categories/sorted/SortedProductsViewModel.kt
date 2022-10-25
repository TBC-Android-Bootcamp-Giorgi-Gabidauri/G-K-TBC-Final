package com.gabo.gk.ui.home.products.categories.sorted

import androidx.lifecycle.ViewModel
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.useCases.product.GetSortedProductsUseCase
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.modelTransformers.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SortedProductsViewModel @Inject constructor(private val getSortedProductsUseCase: GetSortedProductsUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(ViewState())
    val state = _state.asStateFlow()

    suspend fun getSortedProducts(field:String, equalsTo: String) {
        resetViewState()
        _state.value = _state.value.copy(loading = true)
        getSortedProductsUseCase(Pair(field,equalsTo)).collect {
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