package com.gabo.gk.ui.auth.register

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.domain.useCases.auth.RegisterUseCase
import com.gabo.gk.domain.useCases.checkers.RepeatPasswordValidationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val repeatPasswordValidationUseCase: RepeatPasswordValidationUseCase
) : BaseViewModel<String>() {

    fun registerUser(email: String, password: String, repeatPassword: String) {
        resetDefaultViewState()
        viewModelScope.launch {
            when (val msg = repeatPasswordValidationUseCase(Pair(password, repeatPassword))) {
                "welcome" -> {
                    registerUseCase(Pair(email, password)).collect {
                        _defaultState.value = _defaultState.value.copy(msg = it)
                    }
                }
                else -> {
                    _defaultState.value = _defaultState.value.copy(msg = msg)
                }
            }
        }

    }
}