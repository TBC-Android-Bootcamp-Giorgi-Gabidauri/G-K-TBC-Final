package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ProductRepository
import javax.inject.Inject

class SaveProductUseCase @Inject constructor(private val productRepository: ProductRepository) :
    BaseUseCase<ProductModelDomain, Unit> {
    override suspend fun invoke(params: ProductModelDomain) {
        return productRepository.saveProduct(params)
    }
}