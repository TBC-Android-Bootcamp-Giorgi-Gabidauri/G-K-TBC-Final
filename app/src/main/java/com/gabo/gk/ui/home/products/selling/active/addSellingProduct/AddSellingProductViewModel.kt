package com.gabo.gk.ui.home.products.selling.active.addSellingProduct

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabo.gk.domain.useCases.product.UploadProductScenario
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.modelTransformers.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSellingProductViewModel @Inject constructor(
    private val uploadProductScenario: UploadProductScenario,
) :
    ViewModel() {
    private val _state = MutableStateFlow(ViewState())
    val state = _state.asStateFlow()

    fun uploadProduct(product: ProductModelUi, uris: List<Uri>?) {
        resetViewState()
        viewModelScope.launch {
            uploadProductScenario(Pair(product.toDomain(), uris)).collect {
                _state.value = _state.value.copy(uploadInfo = it)
            }
        }
    }

    private fun resetViewState() {
        _state.value = _state.value.copy(uploadInfo = "")
    }

    data class ViewState(
        val uploadInfo: String = ""
    )
}