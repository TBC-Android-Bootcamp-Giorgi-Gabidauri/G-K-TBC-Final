package com.gabo.gk.domain.model

data class TransactionModelDomain(
    val title: String,
    val price: Int,
    val sold: Boolean,
    val purchasedBy: String,
    val images: List<String>?
)