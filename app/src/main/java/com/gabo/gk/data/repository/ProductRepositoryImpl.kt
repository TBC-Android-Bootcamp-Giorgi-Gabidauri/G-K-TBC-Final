package com.gabo.gk.data.repository

import com.gabo.gk.comon.helpers.QueryHelper
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.data.transformers.toDto
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val queryHelper: QueryHelper
) : ProductRepository {
    override suspend fun uploadProduct(model: ProductModelDomain) = flow {
        try {
            fireStore.collection("products").add(model.toDto())
            emit("Uploaded Successfully")
        } catch (e: Exception) {
            emit(e.message.toString())
        }
    }

    override suspend fun getProducts() =
        queryHelper.queryHelper { fireStore.collection("products").get().await() }

    override suspend fun sortProducts(field: String, equalsTo: String) =
        queryHelper.queryHelper {
            fireStore.collection("products").whereEqualTo(field, equalsTo).get()
                .await()
        }

    override suspend fun sortForCurrentUser(uid: String, field: String, equalsTo: Any?) =
        queryHelper.queryHelper {
            fireStore.collection("products").whereEqualTo("uid", uid).whereEqualTo(field, equalsTo)
                .get().await()
        }

    override suspend fun searchProducts(
        field: String, query: String
    ): Flow<Resource<List<ProductModelDomain>>> {
        val fieldPath: String = if (field == "title") "searchList" else field
        return queryHelper.queryHelper {
            query.isNotEmpty().let {
                fireStore.collection("products")
                    .whereArrayContainsAny(fieldPath, listOf(query)).get().await()
            }
        }
    }
}
