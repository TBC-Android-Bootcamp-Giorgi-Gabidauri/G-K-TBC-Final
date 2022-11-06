package com.gabo.gk.ui.home.user.wallet.fillBalance

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.domain.useCases.user.FillBalanceScenario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FillBalanceViewModel@Inject constructor(
    private val fillBalanceScenario: FillBalanceScenario,
) : BaseViewModel<String>() {
    fun fillBalance(amount: Int){
        resetDefaultViewState()
        viewModelScope.launch {
            _defaultState.value = _defaultState.value.copy(loading = true)
            val result = fillBalanceScenario(amount)
            _defaultState.value = _defaultState.value.copy(msg = result, loading = false)
        }
    }
}