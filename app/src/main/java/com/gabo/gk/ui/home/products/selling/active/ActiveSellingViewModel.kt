package com.gabo.gk.ui.home.products.selling.active

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.domain.useCases.product.GetSortedProductsUseCase
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.modelTransformers.toUi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveSellingViewModel @Inject constructor(
    private val getSortedProductsUseCase: GetSortedProductsUseCase,
    private val auth: FirebaseAuth
) : BaseViewModel<List<ProductModelUi>>() {
    fun getSortedProducts(field: String) {
        viewModelScope.launch {
            getData(
                flow = (getSortedProductsUseCase((Pair(field, auth.currentUser?.uid ?: "")))),
                mapper = { it.map { domain -> domain.toUi() } },
                success = null, error = null
            )
        }
    }
}