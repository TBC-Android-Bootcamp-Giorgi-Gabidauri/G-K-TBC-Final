package com.gabo.gk.base

import androidx.lifecycle.ViewModel
import com.gabo.gk.comon.response.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<Ui : Any> : ViewModel() {
    protected val _defaultState =MutableStateFlow(DefaultViewState<Ui>())
    open val defaultState = _defaultState.asStateFlow()
    open suspend fun <T : Any> getData(
        flow: Flow<Resource<T>>,
        mapper: ((T) -> Ui),
        success: ((Resource.Success<T>) -> (Unit))? = null,
        error: ((Resource.Error<T>) -> (Unit))? = null
    ) {
        flow.collect {
            resetDefaultViewState()
            _defaultState.value = _defaultState.value.copy(loading = true)
            when (it) {
                is Resource.Success -> {
                    _defaultState.value = _defaultState.value.copy(
                        loading = false,
                        data = mapper(it.data!!)
                    )
                    success?.invoke(it)
                }
                is Resource.Error -> {
                    _defaultState.value = _defaultState.value.copy(
                        loading = false,
                        msg = it.errorMsg ?: "Something went wrong"
                    )
                    error?.invoke(it)
                }
            }
        }
    }

    protected fun resetDefaultViewState() {
        _defaultState.value = DefaultViewState()
    }

    data class DefaultViewState<Ui : Any>(
        val loading: Boolean = false,
        val data: Ui? = null,
        val msg: String = ""
    )
}