package com.gabo.gk.domain.useCases.images

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.repository.ImagesRepository
import javax.inject.Inject

class GetImageUrlsUseCase @Inject constructor(private val imagesRepository: ImagesRepository) :
    BaseUseCase<Pair<String, String>, MutableList<String>> {
    override suspend fun invoke(params: Pair<String, String>): MutableList<String> {
        return  imagesRepository.getImageUrls(params.first,params.second)
    }
}