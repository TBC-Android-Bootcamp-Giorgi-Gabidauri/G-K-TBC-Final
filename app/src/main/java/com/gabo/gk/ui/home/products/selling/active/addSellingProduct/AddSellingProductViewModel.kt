package com.gabo.gk.ui.home.products.selling.active.addSellingProduct

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.domain.useCases.product.UploadProductScenario
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.modelTransformers.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSellingProductViewModel @Inject constructor(
    private val uploadProductScenario: UploadProductScenario,
) : BaseViewModel<Unit>() {
    fun uploadProduct(product: ProductModelUi, uris: List<Uri>?) {
        resetDefaultViewState()
        viewModelScope.launch {
            _defaultState.value = _defaultState.value.copy(loading = true)
            uploadProductScenario(Pair(product.toDomain(), uris)).collect {
                _defaultState.value = _defaultState.value.copy(msg = it, loading = false)
            }
        }
    }
}