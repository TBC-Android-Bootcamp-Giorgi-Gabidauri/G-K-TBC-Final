package com.gabo.gk.domain.useCases.product

import android.content.Context
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ProductRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSavedProductsScenario @Inject constructor(
    private val productRepository: ProductRepository,
    private val getSavedProductsFromServerUseCase: GetSavedProductsFromServerUseCase,
    private val saveProductToDbUseCase: SaveProductToDbUseCase,
    private val deleteAllProductsFromDb: DeleteAllProductsFromDb,
) :
    BaseUseCase<Unit, Flow<List<ProductModelDomain>>> {
    override suspend fun invoke(params: Unit): Flow<List<ProductModelDomain>> = flow {
        val savedProducts: List<ProductModelDomain>
        when (val result = getSavedProductsFromServerUseCase(Unit)) {
            is Resource.Success -> {
                savedProducts = result.data ?: emptyList()
                if (savedProducts.isNotEmpty()) {
                    deleteAllProductsFromDb(Unit)
                    savedProducts.forEach { saveProductToDbUseCase(it) }
                    emit(savedProducts)
                }
            }
            is Resource.Error -> {
                productRepository.getSavedProductsFromDb().collect{
                    emit(it)
                }
            }
        }
    }
}