package com.gabo.gk.data.repository

import android.net.Uri
import com.gabo.gk.data.global.dataSources.images.ImagesGlobalDataSource
import com.gabo.gk.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesGlobalDataSource: ImagesGlobalDataSource
) :
    ImagesRepository {
    override suspend fun uploadImage(filename: String, uid: String, imgUris: List<Uri>?) =
        imagesGlobalDataSource.uploadImage(filename, uid, imgUris)

    override suspend fun getImageUrls(filename: String, uid: String) =
        imagesGlobalDataSource.getImageUrls(filename, uid)
}