package com.gabo.gk.ui.home.products.selling.sold

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.domain.useCases.product.SortForCurrentUserUseCase
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.model.sort.CurrentUserSortModelUi
import com.gabo.gk.ui.modelTransformers.toDomain
import com.gabo.gk.ui.modelTransformers.toUi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoldProductsViewModel @Inject constructor(
    private val sortForCurrentUserUseCase: SortForCurrentUserUseCase,
    private val auth: FirebaseAuth
) : BaseViewModel<List<ProductModelUi>>() {
    fun getSortedProducts(field: String, equalsTo: Any?) {
        viewModelScope.launch {
            getData(
                flow = (sortForCurrentUserUseCase(
                    CurrentUserSortModelUi(auth.currentUser?.uid ?: "", field, equalsTo).toDomain()
                )),
                mapper = { it.map { domain -> domain.toUi() } },
                success = null, error = null
            )
        }
    }
}