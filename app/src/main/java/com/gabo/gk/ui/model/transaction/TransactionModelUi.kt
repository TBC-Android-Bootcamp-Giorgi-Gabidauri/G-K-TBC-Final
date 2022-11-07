package com.gabo.gk.ui.model.transaction

data class TransactionModelUi(
    val title: String,
    val price: Int,
    val sold: Boolean,
    val purchasedBy: String,
    val images: List<String>?
)
