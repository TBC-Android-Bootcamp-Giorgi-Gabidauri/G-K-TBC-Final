package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(private val repository: ProductRepository) :
    BaseUseCase<Pair<String, String>, Flow<Resource<List<ProductModelDomain>>>> {
    override suspend fun invoke(params: Pair<String, String>): Flow<Resource<List<ProductModelDomain>>> {
        return repository.searchProducts(params.first, params.second)
    }
}