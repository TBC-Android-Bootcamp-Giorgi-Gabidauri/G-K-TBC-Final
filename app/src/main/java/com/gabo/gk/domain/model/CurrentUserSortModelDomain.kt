package com.gabo.gk.domain.model

data class CurrentUserSortModelDomain(
    val uid: String,
    val field: String,
    val equalsTo: Any?
)