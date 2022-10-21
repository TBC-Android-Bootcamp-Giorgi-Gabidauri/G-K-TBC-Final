package com.gabo.gk.data.dto

data class ProductDto(
    val uid: String = "",
    val title: String = "",
    val productCondition:String,
    val description: String?,
    val productType: String?,
    val productCategory: String?,
    val canBeSoldOnline: Boolean?,
    val photos: List<String>?,
    val price: String?,
    val negotiablePrice: Boolean?,
    val sellerName: String?,
    val sellerPhoneNumber: String?,
    val location: String?
)