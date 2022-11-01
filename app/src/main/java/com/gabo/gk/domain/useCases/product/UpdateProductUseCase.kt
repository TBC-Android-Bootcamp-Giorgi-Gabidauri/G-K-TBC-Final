package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(private val productRepository: ProductRepository) :
    BaseUseCase<ProductModelDomain, Flow<String?>> {
    override suspend fun invoke(params: ProductModelDomain): Flow<String?> {
        return productRepository.updateProduct(params)
    }
}