package com.gabo.gk.domain.repository

import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.ProductModelDomain
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<Resource<List<ProductModelDomain>>>
    suspend fun uploadProduct(model: ProductModelDomain): Flow<String>
    suspend fun sortProducts(
        field: String,
        equalsTo: String
    ): Flow<Resource<List<ProductModelDomain>>>

    suspend fun sortForCurrentUser(
        uid: String,
        field: String,
        equalsTo: Any?
    ): Flow<Resource<List<ProductModelDomain>>>

    suspend fun searchProducts(field: String, query: String): Flow<Resource<List<ProductModelDomain>>>
}