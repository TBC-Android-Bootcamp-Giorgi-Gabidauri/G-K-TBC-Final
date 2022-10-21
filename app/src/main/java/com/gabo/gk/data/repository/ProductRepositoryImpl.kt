package com.gabo.gk.data.repository

import com.gabo.gk.domain.repository.ProductRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbReference: DatabaseReference
) : ProductRepository {
    override suspend fun uploadProduct(modelMap: HashMap<String, Any?>) = flow {
        try {
            val uid = auth.currentUser!!.uid
            auth.currentUser?.let {
                dbReference.child("Products").child(uid).child(modelMap["id"].toString())
                    .setValue(modelMap).await()
            }
            if (dbReference.child("Products").child(uid).child(modelMap["id"].toString()).get()
                    .await().exists()
            ) {
                emit("Uploaded Successfully")
            } else {
                emit("Something went wrong")
            }
        } catch (e: Exception) {
            emit(e.message.toString())
        }
    }
}
