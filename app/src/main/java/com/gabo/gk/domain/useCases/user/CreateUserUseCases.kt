package com.gabo.gk.domain.useCases.user

import android.content.Context
import com.gabo.gk.domain.repository.UserRepository
import com.gabo.gk.domain.useCases.auth.RegisterUseCase
import com.gabo.gk.domain.useCases.checkers.CheckEmailFormatUseCase
import com.gabo.gk.domain.useCases.checkers.CheckEmptyFieldsUseCase
import com.gabo.gk.domain.useCases.images.GetImageUrlsUseCase
import com.gabo.gk.domain.useCases.images.UploadImagesUseCase
import com.gabo.gk.domain.useCases.search.CreateSearchSamplesUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

data class CreateUserUseCases @Inject constructor(
    val userRepository: UserRepository,
    val uploadImagesUseCase: UploadImagesUseCase,
    val getImageUrlsUseCase: GetImageUrlsUseCase,
    val createSearchSamplesUseCase: CreateSearchSamplesUseCase,
    val checkEmptyFieldsUseCase: CheckEmptyFieldsUseCase,
    val checkEmailFormatUseCase: CheckEmailFormatUseCase,
    val registerUseCase: RegisterUseCase,
    val auth: FirebaseAuth,
    @ApplicationContext val context: Context
)
