package com.gabo.gk.data.dto

data class ProductDto(
    val uid: String = "",
    val title: String = "",
    val productCondition: String = "",
    val description: String= "",
    val productType: String= "",
    val productCategory: String= "",
    val canBeSoldOnline: Boolean= false,
    val photos: List<String>?= emptyList(),
    val price: String?= "",
    val negotiablePrice: Boolean= false,
    val sellerName: String= "",
    val sellerPhoneNumber: String= "",
    val location: String= "",
    val sold: Boolean = false,
    val searchList: List<String> = emptyList()
)