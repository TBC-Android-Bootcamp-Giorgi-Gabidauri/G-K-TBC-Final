package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.ProductModelDomain
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetActiveSellingProductsUseCase @Inject constructor(
    private val getSortedProductsUseCase: GetSortedProductsUseCase,
    private val auth: FirebaseAuth
) :
    BaseUseCase<String, Flow<Resource<List<ProductModelDomain>>>> {
    override suspend fun invoke(params: String): Flow<Resource<List<ProductModelDomain>>> {
        return getSortedProductsUseCase(Pair(params,auth.currentUser!!.uid)).map {
            when(it){
                is Resource.Success -> {
                    val list = it.data?.toMutableList()
                    it.data?.forEach { product ->
                        if (product.sold) list?.remove(product)
                    }
                    Resource.Success(list)
                }
                is Resource.Error -> Resource.Error(it.errorMsg)
            }
        }
    }
}