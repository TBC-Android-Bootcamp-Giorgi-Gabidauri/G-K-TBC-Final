package com.gabo.gk.domain.model

data class UserModelDomain(
    val id: String = "",
    val uid: String,
    val email: String,
    val password: String,
    val type: String,
    val userName: String,
    val profileImg: String,
    val contactNumber: String,
    var availableAmount: Int,
    val searchList: List<String>,
    val soldProducts: MutableList<ProductModelDomain> = mutableListOf(),
    val purchasedProducts: MutableList<ProductModelDomain> = mutableListOf()
)