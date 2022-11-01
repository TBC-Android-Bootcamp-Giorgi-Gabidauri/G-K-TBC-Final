package com.gabo.gk.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabo.gk.domain.useCases.auth.RegisterUseCase
import com.gabo.gk.domain.useCases.checkers.RepeatPasswordValidationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val repeatPasswordValidationUseCase: RepeatPasswordValidationUseCase
) :
    ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state = _state.asStateFlow()

    fun registerUser(email: String, password: String, repeatPassword: String) {
        resetViewState()
        viewModelScope.launch {
            val msg = repeatPasswordValidationUseCase(Pair(password, repeatPassword))
            when (msg) {
                "welcome" -> {
                    _state.value = _state.value.copy(valid = true)
                    registerUseCase(Pair(email, password)).collect {
                        _state.value =
                            _state.value.copy(registeredSuccessfully = it)
                    }
                }
                else -> {
                    _state.value = _state.value.copy(repeatedPasswordValidation = msg)
                }
            }
        }

    }


    private fun resetViewState() {
        _state.value = _state.value.copy(registeredSuccessfully = "")
    }

    data class ViewState(
        val registeredSuccessfully: String = "",
        val repeatedPasswordValidation: String = "",
        val valid: Boolean = false

    )
}