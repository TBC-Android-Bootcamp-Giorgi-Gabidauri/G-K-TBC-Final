package com.gabo.gk.domain.model

data class FilterModelDomain(
    val showFirst: String = "",
    val type: String = "",
    val status: String ="",
    val anyPrice: Boolean = true,
    val negotiablePrice: Boolean = false,
    val priceFrom: Int = 0,
    val priceTill: Int = 999999999,
    val location: String = "",
    val categoryField: String = "",
    val categoryEqualsTo: String = ""
)
