package com.gabo.gk.ui.home.products.selling.addSellingProduct

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.gabo.gk.domain.useCases.product.UploadProductUseCase
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.modelTransformers.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddSellingProductViewModel @Inject constructor(
    private val uploadProductUseCase: UploadProductUseCase,
) :
    ViewModel() {
    private val _state = MutableStateFlow(ViewState())
    val state = _state.asStateFlow()

    suspend fun uploadProduct(product: ProductModelUi, uris: List<Uri>?) {
        resetViewState()
        uploadProductUseCase(Pair(product.toDomain(), uris)).collect {
            _state.value = _state.value.copy(uploadInfo = it)
        }
    }

//    suspend fun uploadImages(filename: String, uid: String, imgUris: List<Uri>?) {
//        uploadImagesUseCase(Triple(filename,uid,imgUris))
//    }

    private fun resetViewState() {
        _state.value = _state.value.copy(uploadInfo = "")
    }

    data class ViewState(
        val uploadInfo: String = ""
    )
}