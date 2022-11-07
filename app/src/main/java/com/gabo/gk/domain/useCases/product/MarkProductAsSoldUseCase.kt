package com.gabo.gk.domain.useCases.product

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.model.ProductModelDomain
import javax.inject.Inject

class MarkProductAsSoldUseCase @Inject constructor(private val updateProductUseCase: UpdateProductUseCase) :
    BaseUseCase<ProductModelDomain, String> {
    override suspend fun invoke(params: ProductModelDomain): String {
        params.sold = true
        return updateProductUseCase(params)
    }
}