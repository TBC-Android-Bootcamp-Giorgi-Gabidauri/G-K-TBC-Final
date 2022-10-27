package com.gabo.gk.domain.useCases.images

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.model.UploadImageModel
import com.gabo.gk.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadImagesUseCase @Inject constructor(private val imagesRepository: ImagesRepository) :
    BaseUseCase<UploadImageModel, Flow<String?>> {
    override suspend fun invoke(params: UploadImageModel): Flow<String?> {
        return imagesRepository.uploadImage(params.filename, params.uid, params.imgUris)
    }
}
