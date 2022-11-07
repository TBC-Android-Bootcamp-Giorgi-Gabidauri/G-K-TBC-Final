package com.gabo.gk.domain.useCases.user

import android.content.Context
import android.net.Uri
import com.gabo.gk.R
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.model.UploadImageModel
import com.gabo.gk.domain.model.UserModelDomain
import com.gabo.gk.domain.useCases.checkers.CheckIfUserNameEmptyUseCase
import com.gabo.gk.domain.useCases.images.GetImageUrlsUseCase
import com.gabo.gk.domain.useCases.images.UploadImagesUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserProfileScenario @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val uploadImagesUseCase: UploadImagesUseCase,
    private val getImageUrlsUseCase: GetImageUrlsUseCase,
    private val checkIfUsernameEmptyUseCase: CheckIfUserNameEmptyUseCase,
    @ApplicationContext private val context: Context
) : BaseUseCase<Pair<UserModelDomain, Uri?>, Flow<String>> {
    override suspend fun invoke(params: Pair<UserModelDomain, Uri?>): Flow<String> = flow {
        try {
            val checkEmpty = checkIfUsernameEmptyUseCase(params.first)
            if (checkEmpty.isNotEmpty()) emit(checkEmpty)
            else if(params.second != null){
                var photosUploaded = ""
                uploadImagesUseCase(
                    UploadImageModel(params.first.email, params.first.userName, listOf(params.second!!))
                ).collect {
                    photosUploaded = it!!
                }
                val userModel = params.first.copy(
                    profileImg = getImageUrlsUseCase(Pair(params.first.email, params.first.userName))[0]
                )
                when (photosUploaded) {
                    context.getString(R.string.images_uploaded_successfully) -> {
                        emit(updateUserUseCase(userModel))
                    }
                    else -> {
                        emit(photosUploaded)
                    }
                }
            }
            else{
                emit(updateUserUseCase(params.first))
            }
        } catch (e: Exception) {
            emit(e.message.toString())
        }
    }
}