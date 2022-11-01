package com.gabo.gk.data.dto

data class ProductDto(
    val id: String = "",
    val uid: String = "",
    val title: String = "",
    val productCondition: String = "",
    val description: String = "",
    val productType: String = "",
    val productCategory: String = "",
    val canBeSoldOnline: Boolean = false,
    val photos: List<String>? = emptyList(),
    val price: Int = 0,
    val negotiablePrice: Boolean = false,
    val sellerName: String = "",
    val sellerPhoneNumber: String = "",
    val location: String = "",
    val sold: Boolean = false,
    val searchList: List<String> = emptyList(),
    var isSaved: List<String> = emptyList()
)