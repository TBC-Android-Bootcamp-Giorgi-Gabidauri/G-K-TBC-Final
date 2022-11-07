package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSortedProductsUseCase @Inject constructor(private val productRepository: ProductRepository) :
    BaseUseCase<Pair<String,String>, Flow<Resource<List<ProductModelDomain>>>> {
    override suspend fun invoke(params: Pair<String,String>): Flow<Resource<List<ProductModelDomain>>> {
       return productRepository.sortProducts(params.first,params.second)
    }
}