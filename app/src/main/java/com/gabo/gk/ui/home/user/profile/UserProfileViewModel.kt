package com.gabo.gk.ui.home.user.profile

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.useCases.auth.LogOutUseCase
import com.gabo.gk.domain.useCases.user.GetUserUseCase
import com.gabo.gk.ui.model.user.UserModelUi
import com.gabo.gk.ui.modelTransformers.toUi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val auth: FirebaseAuth
) : BaseViewModel<UserModelUi>() {
    init {
        getUser()
    }
    fun logOut() {
        viewModelScope.launch { logOutUseCase(Unit) }
    }

    private fun getUser() {
        viewModelScope.launch {
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