package com.gabo.gk.ui.home.products.selling.sold

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.useCases.product.GetSoldProductsUseCase
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.modelTransformers.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoldProductsViewModel @Inject constructor(
    private val getSoldProductsUseCase: GetSoldProductsUseCase,
) : BaseViewModel<List<ProductModelUi>>() {
    init {
        getSoldProducts()
    }

    fun getSoldProducts() {
        viewModelScope.launch {
            _defaultState.value = _defaultState.value.copy(loading = true)
            when (val result = getSoldProductsUseCase(Unit)) {
                is Resource.Success -> {
                    _defaultState.value = _defaultState.value.copy(
                        data = result.data?.map { it.toUi() }, loading = false
                    )
                }
                is Resource.Error -> _defaultState.value =
                    _defaultState.value.copy(msg = result.errorMsg!!, loading = false)
            }
        }
    }
}