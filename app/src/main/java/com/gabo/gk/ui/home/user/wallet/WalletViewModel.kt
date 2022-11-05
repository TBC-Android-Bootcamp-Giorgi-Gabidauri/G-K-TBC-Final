package com.gabo.gk.ui.home.user.wallet

import android.text.Editable
import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.useCases.formatters.FormatCardNumberUseCase
import com.gabo.gk.domain.useCases.product.GetTransactionsUseCase
import com.gabo.gk.domain.useCases.user.FillBalanceScenario
import com.gabo.gk.domain.useCases.user.GetUserUseCase
import com.gabo.gk.ui.model.transaction.TransactionModelUi
import com.gabo.gk.ui.modelTransformers.toUi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val auth: FirebaseAuth,
    private val formatCardNumberUseCase: FormatCardNumberUseCase,
    private val fillBalanceScenario: FillBalanceScenario
) : BaseViewModel<List<TransactionModelUi>>() {
    init {
        getTransactions()
        getUserBalance()
    }

    private fun getTransactions() {
        viewModelScope.launch {
            when (val result = getTransactionsUseCase(Unit)) {
                is Resource.Success -> _defaultState.value =
                    _defaultState.value.copy(data = result.data?.map { it.toUi() })
                is Resource.Error -> _defaultState.value =
                    _defaultState.value.copy(msg = result.errorMsg!!)
            }
        }
    }

    fun getUserBalance() = flow {
        _defaultState.value = _defaultState.value.copy(loading = true)
        when (val result = getUserUseCase(auth.currentUser!!.uid)) {
            is Resource.Success -> {
                emit(result.data?.availableAmount?.toDouble().toString())
                _defaultState.value = _defaultState.value.copy(loading = false)
            }
            is Resource.Error -> _defaultState.value =
                _defaultState.value.copy(msg = result.errorMsg!!)
        }
    }

    fun formatCardNumber(input: Editable) {
        formatCardNumberUseCase(input)
    }

    fun fillBalance(amount: Int) {
        resetDefaultViewState()
        viewModelScope.launch {
            _defaultState.value = _defaultState.value.copy(loading = true)
            val result = fillBalanceScenario(amount)
            _defaultState.value = _defaultState.value.copy(msg = result, loading = false)
        }
    }
}