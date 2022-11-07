package com.gabo.gk.ui.home.user.profile.editProfile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.domain.useCases.user.UpdateUserProfileScenario
import com.gabo.gk.ui.model.user.UserModelUi
import com.gabo.gk.ui.modelTransformers.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val updateUserProfileScenario: UpdateUserProfileScenario) :
    BaseViewModel<String>() {

    fun updateProfile(userModel: UserModelUi, uri: Uri?) {
        viewModelScope.launch {
            _defaultState.value = _defaultState.value.copy(loading = true)
            updateUserProfileScenario(Pair(userModel.toDomain(), uri)).collect {
                _defaultState.value = _defaultState.value.copy(msg = it, loading = false)
            }
        }
    }
}