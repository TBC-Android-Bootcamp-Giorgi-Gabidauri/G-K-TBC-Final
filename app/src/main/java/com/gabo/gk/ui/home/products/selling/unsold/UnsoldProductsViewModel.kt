package com.gabo.gk.ui.home.products.selling.unsold

import androidx.lifecycle.ViewModel
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.useCases.product.SortForCurrentUserUseCase
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.modelTransformers.toUi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UnsoldProductsViewModel @Inject constructor(
    private val sortForCurrentUserUseCase: SortForCurrentUserUseCase,
    private val auth: FirebaseAuth
) :
    ViewModel() {
    private val _state = MutableStateFlow(ViewState())
    val state = _state.asStateFlow()

    suspend fun getSortedProducts(field: String, equalsTo: Any?) {
        resetViewState()
        _state.value = _state.value.copy(loading = true)
        sortForCurrentUserUseCase(Triple(auth.currentUser?.uid ?: "", field, equalsTo)).collect {
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