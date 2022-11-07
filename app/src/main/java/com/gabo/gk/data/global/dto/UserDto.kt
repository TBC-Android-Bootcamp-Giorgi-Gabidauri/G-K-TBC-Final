package com.gabo.gk.data.global.dto

import com.gabo.gk.domain.model.NotificationModel

data class UserDto(
    var id: String = "",
    val uid: String = "",
    val email: String = "",
    val password: String = "",
    val type: String = "",
    val userName: String = "",
    val profileImg: String = "",
    val contactNumber: String = "",
    val availableAmount: Int = 0,
    val searchList: List<String> = emptyList(),
    val soldProducts: MutableList<ProductDto> = mutableListOf(),
    val purchasedProducts: MutableList<ProductDto> = mutableListOf(),
    val token: String = "",
    val savedProducts: MutableList<ProductDto> = mutableListOf(),
    val notifications: MutableList<NotificationModel> = mutableListOf()
)