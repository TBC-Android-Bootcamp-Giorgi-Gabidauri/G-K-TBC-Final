package com.gabo.gk.domain.useCases.product

import android.net.Uri
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.helpers.HashModelHelper
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.repository.ImagesRepository
import com.gabo.gk.domain.repository.ProductRepository
import com.gabo.gk.domain.useCases.images.UploadImagesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UploadProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val imagesRepository: ImagesRepository,
    private val uploadImagesUseCase: UploadImagesUseCase,
    private val hashModelHelper: HashModelHelper
) : BaseUseCase<Pair<ProductModelDomain, List<Uri>?>, Flow<String>> {
    override suspend fun invoke(params: Pair<ProductModelDomain, List<Uri>?>): Flow<String> {
        return try {
            var photosUploaded = ""
            uploadImagesUseCase(// upload Photos
                Triple(params.first.title, params.first.uid, params.second)
            ).collect {
                photosUploaded = it!!
            }
            val productModel = params.first.copy(// get and write list of photo Urls in model
                photos = imagesRepository.getImageUrls(params.first.title, params.first.uid)
            )
            if (photosUploaded == "images uploaded successfully") { // upload product
                return productRepository.uploadProduct(hashModelHelper.modelToHashMap(productModel))
            } else {
                return flow { emit(photosUploaded) }
            }
        } catch (e: Exception) {
            flow { emit(e.message!!) }
        }
    }
}