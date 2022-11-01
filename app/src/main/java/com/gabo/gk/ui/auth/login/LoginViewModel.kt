package com.gabo.gk.ui.auth.login

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.domain.useCases.auth.LogInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val logInUseCase: LogInUseCase) :
    BaseViewModel<String>() {

    fun loginUser(email: String, password: String) {
        resetDefaultViewState()
        viewModelScope.launch {
            logInUseCase(Pair(email, password)).collect {
                _defaultState.value =
                    _defaultState.value.copy(msg = it)
            }
        }
    }

}