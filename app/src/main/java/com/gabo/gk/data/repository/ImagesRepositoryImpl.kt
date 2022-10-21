package com.gabo.gk.data.repository

import android.net.Uri
import com.gabo.gk.domain.repository.ImagesRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(private val firebaseStorage: FirebaseStorage) :
    ImagesRepository {
    override suspend fun uploadImage(filename: String, uid: String, imgUris: List<Uri>?) = flow {
        try {
            imgUris?.let {
                it.forEach { uri -> //upload
                    val id = (0..999999999999999).random()
                    firebaseStorage.reference.child("images/$uid/$filename/$id").putFile(uri)
                        .await()
                }
                val imgUrls = getImageUrls(filename, uid)
                when {
                    imgUrls.isNotEmpty() -> emit("images uploaded successfully")
                    else -> emit("something went wrong")
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                emit(e.message)
            }
        }
    }

    override suspend fun getImageUrls(filename: String, uid: String): MutableList<String> {
        val imgUrls = mutableListOf<String>()
        val images = firebaseStorage.reference.child("images/").child(uid)
            .child(filename).listAll().await() // get list of photos
        for (image in images.items) { //convert to list of Urls
            val url = image.downloadUrl.await()
            imgUrls.add(url.toString())
        }
        return imgUrls
    }
}