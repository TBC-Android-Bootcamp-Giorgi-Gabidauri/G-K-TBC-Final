package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteAllProductsFromDb @Inject constructor(private val productRepository: ProductRepository) :
    BaseUseCase<Unit, Unit> {
    override suspend fun invoke(params: Unit) {
        productRepository.deleteAllProductsFromDb()
    }

}