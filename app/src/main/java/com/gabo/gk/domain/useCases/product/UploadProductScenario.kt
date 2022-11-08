package com.gabo.gk.domain.useCases.product

import android.net.Uri
import com.gabo.gk.R
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.model.UploadImageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UploadProductScenario @Inject constructor(
    private val useCases: UploadProductUseCases
) : BaseUseCase<Pair<ProductModelDomain, List<Uri>?>, Flow<String>> {
    override suspend fun invoke(params: Pair<ProductModelDomain, List<Uri>?>): Flow<String> {
        return try {
            var photosUploaded = ""
            useCases.uploadImagesUseCase(
                UploadImageModel(params.first.title, params.first.uid, params.second)
            ).collect {
                photosUploaded = it!!
            }
            val productModel = params.first.copy(
                photos = useCases.getImageUrlsUseCase(Pair(params.first.title, params.first.uid)),
                searchList = useCases.createSearchSamplesUseCase(params.first.title)
            )
            if (photosUploaded == useCases.context.getString(R.string.images_uploaded_successfully)) {
                return useCases.productRepository.uploadProduct(productModel)
            } else {
                return flow { emit(photosUploaded) }
            }
        } catch (e: Exception) {
            flow { emit(e.message!!) }
        }
    }
}