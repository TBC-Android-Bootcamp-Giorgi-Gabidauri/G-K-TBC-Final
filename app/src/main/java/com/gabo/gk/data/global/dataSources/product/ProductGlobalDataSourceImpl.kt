package com.gabo.gk.data.global.dataSources.product

import android.content.Context
import android.util.Log
import com.gabo.gk.R
import com.gabo.gk.comon.constants.FIELD_SEARCH_LIST
import com.gabo.gk.comon.constants.FIELD_TITLE
import com.gabo.gk.comon.constants.FIELD_UID
import com.gabo.gk.comon.constants.PRODUCTS_STORAGE
import com.gabo.gk.comon.helpers.QueryHelper
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.data.global.dto.ProductDto
import com.gabo.gk.data.transformers.toDto
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.notification.apis.ProductNotificationApi
import com.gabo.gk.notification.model.product.ProductPushNotification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductGlobalDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val queryHelper: QueryHelper,
    private val auth: FirebaseAuth,
    private val productNotificationApi: ProductNotificationApi,
    @ApplicationContext private val context: Context
) :ProductGlobalDataSource{
    override suspend fun uploadProduct(model: ProductModelDomain) = flow {
        try {
            fireStore.collection(PRODUCTS_STORAGE).add(model.toDto()).await()
            emit(context.getString(R.string.uploaded_successfully))
        } catch (e: Exception) {
            emit(e.message.toString())
        }
    }

    override suspend fun getProducts() =
        queryHelper.productQueryHelper { fireStore.collection(PRODUCTS_STORAGE).get().await() }

    override  suspend fun sortProducts(field: String, equalsTo: String) =
        queryHelper.productQueryHelper {
            fireStore.collection(PRODUCTS_STORAGE).whereEqualTo(field, equalsTo).get().await()
        }

    override suspend fun sortForCurrentUser(uid: String, field: String, equalsTo: Any?) =
        queryHelper.productQueryHelper {
            fireStore.collection(PRODUCTS_STORAGE).whereEqualTo(FIELD_UID, uid)
                .whereEqualTo(field, equalsTo).get().await()
        }

    override suspend fun searchProducts(
        field: String,
        query: String
    ): Flow<Resource<List<ProductModelDomain>>> {
        val fieldPath: String = if (field == FIELD_TITLE) FIELD_SEARCH_LIST else field
        return queryHelper.productQueryHelper {
            query.isNotEmpty().let {
                fireStore.collection(PRODUCTS_STORAGE)
                    .whereArrayContainsAny(fieldPath, listOf(query)).get().await()
            }
        }
    }

    override suspend fun updateProduct(product: ProductDto): String {
        return try {
            if (!auth.currentUser?.uid.isNullOrEmpty()) {
                fireStore.collection(PRODUCTS_STORAGE).document(product.id).set(product)
                    .await()
                (context.getString(R.string.product_update_successfully))
            } else {
                (context.getString(R.string.user_is_not_exist))
            }
        } catch (e: Exception) {
            (e.message.toString())
        }
    }

    override suspend fun deleteProduct(product: ProductDto): String {
        return try {
            if (!auth.currentUser?.uid.isNullOrEmpty()) {
                fireStore.collection(PRODUCTS_STORAGE).document(product.id).delete().await()
                (context.getString(R.string.product_deleted_successfully))
            } else {
                (context.getString(R.string.user_is_not_exist))
            }
        } catch (e: Exception) {
            (e.message.toString())
        }
    }

    override suspend fun sendNotification(notification: ProductPushNotification) {
        try {
            val response = productNotificationApi.postNotification(notification)
            if (response.isSuccessful) {
            } else {
                Log.e("notnotnot", response.errorBody().toString())
            }
        } catch (e: Exception) {
            Log.e("notnotnot", e.toString())
        }
    }
}