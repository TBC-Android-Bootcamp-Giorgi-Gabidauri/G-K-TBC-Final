package com.gabo.gk.ui

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.useCases.user.GetUserUseCase
import com.gabo.gk.domain.useCases.user.UpdateUserUseCase
import com.gabo.gk.ui.model.user.UserModelUi
import com.gabo.gk.ui.modelTransformers.toDomain
import com.gabo.gk.ui.modelTransformers.toUi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val auth: FirebaseAuth,
    private val updateUserUseCase: UpdateUserUseCase
) :
    BaseViewModel<UserModelUi>() {
    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            if (auth.currentUser != null){
                _defaultState.value = _defaultState.value.copy(loading = true)
                when (val result = getUserUseCase(auth.currentUser!!.uid)) {
                    is Resource.Success -> {
                        _defaultState.value =
                            _defaultState.value.copy(data = result.data?.toUi(), loading = false)
                    }
                    is Resource.Error ->
                        _defaultState.value =
                            _defaultState.value.copy(msg = result.errorMsg!!, loading = false)
                }
            }
        }
    }
    fun updateUser(userModelUi: UserModelUi){
        viewModelScope.launch {
            val result = updateUserUseCase(userModelUi.toDomain())
            if (result.isNotEmpty()) _defaultState.value = _defaultState.value.copy(msg = result)
        }
    }
}