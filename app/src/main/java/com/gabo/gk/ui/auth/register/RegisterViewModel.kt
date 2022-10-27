package com.gabo.gk.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabo.gk.domain.useCases.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state = _state.asStateFlow()

    fun registerUser(email: String, password: String) {
        resetViewState()
        viewModelScope.launch {
            registerUseCase(Pair(email, password)).collect {
                _state.value =
                    _state.value.copy(registeredSuccessfully = it)
            }
        }
    }

    private fun resetViewState() {
        _state.value = _state.value.copy(registeredSuccessfully = "")
    }

    data class ViewState(
        val registeredSuccessfully: String = ""
    )
}

