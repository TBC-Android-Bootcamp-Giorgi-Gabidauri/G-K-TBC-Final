package com.gabo.gk.data.local.dataSources

import com.gabo.gk.domain.model.ProductModelDomain
import kotlinx.coroutines.flow.Flow

interface ProductLocalDataSource {
    fun getSavedProducts(): Flow<List<ProductModelDomain>>
    suspend fun saveProduct(product: ProductModelDomain)
    suspend fun deleteProduct(id: String)
    suspend fun deleteAll()
}