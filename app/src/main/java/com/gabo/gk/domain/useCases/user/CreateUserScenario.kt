package com.gabo.gk.domain.useCases.user

import android.net.Uri
import com.gabo.gk.R
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.constants.TOKEN
import com.gabo.gk.domain.model.UploadImageModel
import com.gabo.gk.domain.model.UserModelDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CreateUserScenario @Inject constructor(
    private val createUserUseCases: CreateUserUseCases
) : BaseUseCase<Pair<UserModelDomain, Uri>, Flow<String>> {
    override suspend fun invoke(params: Pair<UserModelDomain, Uri>) = flow {
        try {
            with(createUserUseCases){
                val checkEmpty = checkEmptyFieldsUseCase(params.first)
                val checkEmailFormat = checkEmailFormatUseCase(params.first.email)
                when {
                    checkEmpty.isNotEmpty() -> emit(checkEmpty)
                    checkEmailFormat.isNotEmpty() -> emit(checkEmailFormat)
                }
                var photosUploaded = ""
                uploadImagesUseCase(
                    UploadImageModel(params.first.email, params.first.userName, listOf(params.second))
                ).collect {
                    photosUploaded = it!!
                }
                var token = ""
                firebaseMessaging.token.addOnSuccessListener {
                    token = it
                    preferences.edit()?.putString(TOKEN, it)?.apply()
                }.await()
                if (token.isNotEmpty()){
                    var userModel = params.first.copy(
                        profileImg = getImageUrlsUseCase(
                            Pair(params.first.email, params.first.userName)
                        )[0],
                        searchList = createSearchSamplesUseCase(params.first.userName),
                        token = token
                    )
                    when (photosUploaded) {
                        createUserUseCases.context.getString(R.string.images_uploaded_successfully) -> {
                            registerUseCase(
                                Pair(params.first.email, params.first.password)
                            ).collect {
                                userModel =
                                    userModel.copy(uid = auth.currentUser?.uid ?: "")
                                if (userModel.uid.isNotEmpty()) {
                                    emit(userRepository.createUser(userModel))
                                } else {
                                    emit(it)
                                }
                            }
                        }
                        else -> {
                            emit(photosUploaded)
                        }
                    }
                }else{
                    emit(context.getString(R.string.could_not_get_token))
                }
            }
        } catch (e: Exception) {
            emit(e.message.toString())
        }
    }
}