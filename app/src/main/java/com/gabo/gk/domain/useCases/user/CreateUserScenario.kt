package com.gabo.gk.domain.useCases.user

import android.net.Uri
import com.gabo.gk.R
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.model.UploadImageModel
import com.gabo.gk.domain.model.UserModelDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateUserScenario @Inject constructor(
    private val createUserUseCases: CreateUserUseCases
) : BaseUseCase<Pair<UserModelDomain, Uri>, Flow<String>> {
    override suspend fun invoke(params: Pair<UserModelDomain, Uri>) = flow {
        try {
            val checkEmpty = createUserUseCases.checkEmptyFieldsUseCase(params.first)
            val checkEmailFormat = createUserUseCases.checkEmailFormatUseCase(params.first.email)
            when {
                checkEmpty.isNotEmpty() -> emit(checkEmpty)
                checkEmailFormat.isNotEmpty() -> emit(checkEmailFormat)
            }
            var photosUploaded = ""
            createUserUseCases.uploadImagesUseCase(
                UploadImageModel(params.first.email, params.first.userName, listOf(params.second))
            ).collect {
                photosUploaded = it!!
            }
            var userModel = params.first.copy(
                profileImg = createUserUseCases.getImageUrlsUseCase(
                    Pair(
                        params.first.email,
                        params.first.userName
                    )
                )[0],
                searchList = createUserUseCases.createSearchSamplesUseCase(params.first.userName)
            )
            when (photosUploaded) {
                createUserUseCases.context.getString(R.string.images_uploaded_successfully) -> {
                    createUserUseCases.registerUseCase(Pair(params.first.email, params.first.password)).collect {
                        userModel = userModel.copy(uid = createUserUseCases.auth.currentUser?.uid ?: "")
                        if (userModel.uid.isNotEmpty()) {
                            emit(createUserUseCases.userRepository.createUser(userModel))
                        } else {
                            emit(it)
                        }
                    }
                }
                else -> {
                    emit(photosUploaded)
                }
            }
        } catch (e: Exception) {
            emit(e.message.toString())
        }
    }
}