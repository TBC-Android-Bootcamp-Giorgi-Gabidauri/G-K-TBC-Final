package com.gabo.gk.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    suspend fun uploadImage(filename: String, uid: String, imgUris: List<Uri>?): Flow<String?>
    suspend fun getImageUrls(filename: String, uid: String): MutableList<String>
}