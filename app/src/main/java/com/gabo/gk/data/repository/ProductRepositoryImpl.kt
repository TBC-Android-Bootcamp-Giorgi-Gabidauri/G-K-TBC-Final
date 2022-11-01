package com.gabo.gk.data.repository

import android.content.Context
import com.gabo.gk.R
import com.gabo.gk.comon.constants.FIELD_SEARCH_LIST
import com.gabo.gk.comon.constants.FIELD_TITLE
import com.gabo.gk.comon.constants.FIELD_UID
import com.gabo.gk.comon.constants.PRODUCTS_STORAGE
import com.gabo.gk.comon.helpers.QueryHelper
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.data.local.database.ProductsDatabase
import com.gabo.gk.data.transformers.toDomain
import com.gabo.gk.data.transformers.toDto
import com.gabo.gk.data.transformers.toEntity
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val queryHelper: QueryHelper,
    private val database: ProductsDatabase,
    @ApplicationContext private val context: Context
) : ProductRepository {
    override suspend fun uploadProduct(model: ProductModelDomain) = flow {
        try {
            fireStore.collection(PRODUCTS_STORAGE).add(model.toDto())
            emit(context.getString(R.string.uploaded_successfully))
        } catch (e: Exception) {
            emit(e.message.toString())
        }
    }

    override suspend fun getProducts() =
        queryHelper.queryHelper { fireStore.collection(PRODUCTS_STORAGE).get().await() }

    override suspend fun sortProducts(field: String, equalsTo: String) =
        queryHelper.queryHelper {
            fireStore.collection(PRODUCTS_STORAGE).whereEqualTo(field, equalsTo).get()
                .await()
        }

    override suspend fun sortForCurrentUser(uid: String, field: String, equalsTo: Any?) =
        queryHelper.queryHelper {
            fireStore.collection(PRODUCTS_STORAGE).whereEqualTo(FIELD_UID, uid)
                .whereEqualTo(field, equalsTo)
                .get().await()
        }

    override suspend fun searchProducts(
        field: String, query: String
    ): Flow<Resource<List<ProductModelDomain>>> {
        val fieldPath: String = if (field == FIELD_TITLE) FIELD_SEARCH_LIST else field
        return queryHelper.queryHelper {
            query.isNotEmpty().let {
                fireStore.collection(PRODUCTS_STORAGE)
                    .whereArrayContainsAny(fieldPath, listOf(query)).get().await()
            }
        }
    }

    override suspend fun updateProduct(newProduct: ProductModelDomain) = flow {
        try {
            val gString = Gson().toJson(newProduct).toString()
            val mapType = object : TypeToken<Map<String, Any?>?>() {}.type
            val map = Gson().fromJson<Map<String, Any?>?>(gString, mapType)
            try {
                fireStore.collection(PRODUCTS_STORAGE).document(newProduct.id)
                    .set(map, SetOptions.merge())
                emit("Product updated successfully")
            } catch (e: Exception) {
                emit(e.message)
            }

        } catch (e: Exception) {
            emit(e.message)
        }

    }

    override suspend fun getSavedProducts(): Flow<List<ProductModelDomain>> {
        return database.getPurchaseDao.getItems().map { it.map { entity -> entity.toDomain() } }
    }

    override suspend fun saveProduct(product: ProductModelDomain) {
        database.getPurchaseDao.saveItem(product.toEntity())
    }

    override suspend fun deleteProduct(id: String) {
        database.getPurchaseDao.deleteItem(id)
    }
}

