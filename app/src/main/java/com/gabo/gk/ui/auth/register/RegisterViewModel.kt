package com.gabo.gk.ui.auth.register

import androidx.lifecycle.ViewModel
import com.gabo.gk.domain.useCases.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state = _state.asStateFlow()

    suspend fun registerUser(email: String, password: String) {
        resetViewState()
        registerUseCase(Pair(email, password)).collect {
            _state.value =
                _state.value.copy(registerOk = it)
        }
    }

    private fun resetViewState() {
        _state.value = _state.value.copy(registerOk = "")
    }

    data class ViewState(
        val registerOk: String = ""
    )
}