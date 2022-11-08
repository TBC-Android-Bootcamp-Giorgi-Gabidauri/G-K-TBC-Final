package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.ProductModelDomain
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSimilarProductsUseCase @Inject constructor(
    private val getSortedProductsUseCase: GetSortedProductsUseCase,
    private val auth: FirebaseAuth
) :
    BaseUseCase<Pair<String, String>, Flow<Resource<List<ProductModelDomain>>>> {
    override suspend fun invoke(params: Pair<String, String>): Flow<Resource<List<ProductModelDomain>>> {
        return getSortedProductsUseCase(params).map {
            when (it) {
                is Resource.Success -> {
                    val list = it.data?.toMutableList()
                    it.data?.forEach { product ->
                        when {
                            product.uid == auth.currentUser!!.uid || product.sold -> {
                                list?.remove(product)
                            }
                        }
                    }
                    Resource.Success(list)
                }
                is Resource.Error -> Resource.Error(it.errorMsg)
            }
        }
    }
}