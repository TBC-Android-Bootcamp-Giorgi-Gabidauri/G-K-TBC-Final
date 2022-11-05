package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedProductsUseCase @Inject constructor(private val productRepository: ProductRepository) :
    BaseUseCase<Unit, Flow<List<ProductModelDomain>>> {
    override suspend fun invoke(params: Unit): Flow<List<ProductModelDomain>> {
        return productRepository.getSavedProductsFromDb()
    }
}