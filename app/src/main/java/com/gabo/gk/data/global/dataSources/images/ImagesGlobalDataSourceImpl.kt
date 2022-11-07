package com.gabo.gk.data.global.dataSources.images

import android.content.Context
import android.net.Uri
import com.gabo.gk.R
import com.gabo.gk.comon.constants.IMAGES_STORAGE
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ImagesGlobalDataSourceImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    @ApplicationContext private val context: Context
) : ImagesGlobalDataSource {
    override suspend fun uploadImage(filename: String, uid: String, imgUris: List<Uri>?) = flow {
        try {
            imgUris?.let {
                it.forEach { uri -> //upload
                    uniqueImageId(uid, filename)
                    firebaseStorage.reference.child(
                        "$IMAGES_STORAGE/$uid/$filename/${uniqueImageId(uid, filename)}"
                    ).putFile(uri).await()
                }
                val imgUrls = getImageUrls(filename, uid)
                when {
                    imgUrls.isNotEmpty() -> emit(context.getString(R.string.images_uploaded_successfully))
                    else -> emit(context.getString(R.string.something_went_wrong))
                }
            }
        } catch (e: Exception) {
            emit(e.message)
        }
    }

    override suspend fun getImageUrls(filename: String, uid: String): MutableList<String> {
        val imgUrls = mutableListOf<String>()
        val images = firebaseStorage.reference.child("$IMAGES_STORAGE/").child(uid)
            .child(filename).listAll().await() // get list of photos
        for (image in images.items) { //convert to list of Urls
            val url = image.downloadUrl.await()
            imgUrls.add(url.toString())
        }
        return imgUrls
    }

    private suspend fun uniqueImageId(uid: String, filename: String): Long {
        val id = (0..999999999999999).random()
        return try {
            val url =
                firebaseStorage.reference.child("$IMAGES_STORAGE/$uid/$filename/$id").downloadUrl.await()
            if (url != null) {
                id
            } else {
                uniqueImageId(uid, filename)
            }
        } catch (e: Exception) {
            id
        }
    }
}