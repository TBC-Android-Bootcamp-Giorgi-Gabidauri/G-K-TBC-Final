package com.gabo.gk.ui.model.filter

data class FilterModelUi(
    val showFirst: String = "",
    val type: String = "",
    val status: String = "",
    val anyPrice: Boolean = true,
    val negotiablePrice: Boolean = false,
    val priceFrom: Int = 0,
    val priceTill: Int = 999999999,
    val location: String = "",
    val categoryField: String = "",
    val categoryEqualsTo: String = ""
)
