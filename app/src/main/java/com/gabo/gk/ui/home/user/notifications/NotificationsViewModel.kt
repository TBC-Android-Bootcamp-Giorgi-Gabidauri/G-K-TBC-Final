package com.gabo.gk.ui.home.user.notifications

import androidx.lifecycle.viewModelScope
import com.gabo.gk.base.BaseViewModel
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.NotificationModel
import com.gabo.gk.domain.useCases.product.GetNotificationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase
) : BaseViewModel<List<NotificationModel>>() {
    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            _defaultState.value =
                _defaultState.value.copy(loading = false)
            when (val result = getNotificationsUseCase(Unit)) {
                is Resource.Success -> _defaultState.value =
                    _defaultState.value.copy(data = result.data, loading = false)
                is Resource.Error -> _defaultState.value =
                    _defaultState.value.copy(msg = result.errorMsg!!, loading = false)
            }
        }
    }
}