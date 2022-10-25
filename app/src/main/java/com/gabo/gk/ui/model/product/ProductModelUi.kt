package com.gabo.gk.ui.model.product

data class ProductModelUi(
    val uid: String = "",
    val title: String = "",
    val productCondition:String = "Any",
    val description: String = "",
    val productType: String,
    val productCategory: String,
    val canBeSoldOnline: Boolean = false,
    val photos: List<String>?,
    val price: String?,
    val negotiablePrice: Boolean,
    val sellerName: String,
    val sellerPhoneNumber: String,
    val location: String,
    val sold: Boolean = false,
    val searchList: List<String> = emptyList()
)




