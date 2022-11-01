package com.gabo.gk.data.repository

import com.gabo.gk.data.global.dataSources.ProductGlobalDataSource
import com.gabo.gk.data.local.dataSources.ProductLocalDataSource
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productGlobalDataSource: ProductGlobalDataSource,
    private val productLocalDataSource: ProductLocalDataSource
) : ProductRepository {
    override suspend fun uploadProduct(model: ProductModelDomain) = productGlobalDataSource.uploadProduct(model)

    override suspend fun getProducts() = productGlobalDataSource.getProducts()

    override suspend fun sortProducts(field: String, equalsTo: String) = productGlobalDataSource.sortProducts(field, equalsTo)

    override suspend fun sortForCurrentUser(uid: String, field: String, equalsTo: Any?) =
        productGlobalDataSource.sortForCurrentUser(uid, field, equalsTo)

    override suspend fun searchProducts(field: String, query: String)= productGlobalDataSource.searchProducts(field, query)

    override suspend fun updateProduct(newProduct: ProductModelDomain) = productGlobalDataSource.updateProduct(newProduct)

    override suspend fun getSavedProducts()=productLocalDataSource.getSavedProducts()

    override suspend fun saveProduct(product: ProductModelDomain) { productLocalDataSource.saveProduct(product) }

    override suspend fun deleteProduct(id: String) { productLocalDataSource.deleteProduct(id) }
}

