package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteProductFromDbUseCase @Inject constructor(private val productRepository: ProductRepository) :
    BaseUseCase<String, Unit> {
    override suspend fun invoke(params: String) {
        productRepository.deleteProductFromDb(params)
    }
}