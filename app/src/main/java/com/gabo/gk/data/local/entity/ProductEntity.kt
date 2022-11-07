package com.gabo.gk.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gabo.gk.comon.constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
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
    var isSaved: MutableList<String> = mutableListOf(),
    val purchasedBy: String = ""
)