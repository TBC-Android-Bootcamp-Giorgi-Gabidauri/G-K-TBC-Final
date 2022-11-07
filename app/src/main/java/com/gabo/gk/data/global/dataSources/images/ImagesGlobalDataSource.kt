package com.gabo.gk.data.global.dataSources.images

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface ImagesGlobalDataSource {
    suspend fun uploadImage(filename: String, uid: String, imgUris: List<Uri>?): Flow<String?>
    suspend fun getImageUrls(filename: String, uid: String): MutableList<String>
}