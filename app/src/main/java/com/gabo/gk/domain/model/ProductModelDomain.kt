package com.gabo.gk.domain.model

data class ProductModelDomain(
    val id: String = "",
    val uid: String = "",
    val title: String = "",
    val productCondition: String,
    val description: String = "",
    val productType: String,
    val productCategory: String,
    val canBeSoldOnline: Boolean = false,
    val photos: List<String>?,
    val price: Int,
    val negotiablePrice: Boolean = false,
    val sellerName: String = "",
    val sellerPhoneNumber: String = "",
    val location: String,
    var sold: Boolean = false,
    val searchList: List<String> = emptyList(),
    var isSaved: MutableList<String> = mutableListOf(),
    var purchasedBy: String = ""
)
