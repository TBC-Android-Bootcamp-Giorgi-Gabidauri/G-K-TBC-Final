package com.gabo.gk.ui.model.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModelUi(
    val id: String = "",
    val uid: String = "",
    val title: String = "",
    val productCondition: String = "Any",
    val description: String = "",
    val productType: String,
    val productCategory: String,
    val canBeSoldOnline: Boolean = false,
    val photos: List<String>?,
    val price: Int,
    val negotiablePrice: Boolean,
    val sellerName: String,
    val sellerPhoneNumber: String,
    val location: String,
    val sold: Boolean = false,
    val searchList: List<String> = emptyList(),
    var isSaved: List<String> = emptyList()
) : Parcelable




