package com.gabo.gk.domain.useCases.product

import android.content.Context
import com.gabo.gk.domain.repository.ProductRepository
import com.gabo.gk.domain.useCases.images.UploadImagesUseCase
import com.gabo.gk.domain.useCases.search.CreateSearchSamplesUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

data class UploadProductUseCases@Inject constructor(
     val productRepository: ProductRepository,
     val uploadImagesUseCase: UploadImagesUseCase,
     val getImageUrlsUseCase: GetImageUrlsUseCase,
     val createSearchSamplesUseCase: CreateSearchSamplesUseCase,
    @ApplicationContext  val context: Context
)
