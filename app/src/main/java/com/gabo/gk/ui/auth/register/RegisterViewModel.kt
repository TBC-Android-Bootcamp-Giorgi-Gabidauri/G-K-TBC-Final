package com.gabo.gk.ui.auth.register

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.domain.useCases.checkers.RepeatPasswordValidationUseCase
import com.gabo.gk.domain.useCases.user.CreateUserScenario
import com.gabo.gk.ui.model.user.UserModelUi
import com.gabo.gk.ui.modelTransformers.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repeatPasswordValidationUseCase: RepeatPasswordValidationUseCase,
    private val createUserScenario: CreateUserScenario
) : BaseViewModel<String>() {

    fun registerUser(userModel: UserModelUi, repeatPassword: String, uri: Uri) {
        resetDefaultViewState()
        viewModelScope.launch {
            _defaultState.value = _defaultState.value.copy(loading = true)
            when (val msg =
                repeatPasswordValidationUseCase(Pair(userModel.password, repeatPassword))) {
                "" -> {
                    createUserScenario(Pair(userModel.toDomain(), uri)).collect {
                        _defaultState.value = _defaultState.value.copy(msg = it, loading = false)
                    }
                }
                else -> _defaultState.value = _defaultState.value.copy(msg = msg, loading = false)
            }
        }

    }
}