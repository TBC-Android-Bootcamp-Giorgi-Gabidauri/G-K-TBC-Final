package com.gabo.gk.domain.useCases.images

import android.net.Uri
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadImagesUseCase @Inject constructor(private val imagesRepository: ImagesRepository) :
    BaseUseCase<Triple<String, String, List<Uri>?>, Flow<String?>> {
    override suspend fun invoke(params: Triple<String, String, List<Uri>?>): Flow<String?> {
        return imagesRepository.uploadImage(params.first, params.second, params.third)
    }
}