package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ProductRepository
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) : BaseUseCase<ProductModelDomain, String> {
    override suspend fun invoke(params: ProductModelDomain): String {
        return productRepository.updateProduct(params)
    }
}
