package com.gabo.gk.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabo.gk.domain.useCases.auth.LogInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val logInUseCase: LogInUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state = _state.asStateFlow()

    fun loginUser(email: String, password: String) {
        resetViewState()
        viewModelScope.launch {
            logInUseCase(Pair(email, password)).collect {
                _state.value =
                    _state.value.copy(loginOk = it)
            }
        }
    }

    private fun resetViewState() {
        _state.value = _state.value.copy(loginOk = "")
    }

    data class ViewState(
        val loginOk: String = ""
    )
}