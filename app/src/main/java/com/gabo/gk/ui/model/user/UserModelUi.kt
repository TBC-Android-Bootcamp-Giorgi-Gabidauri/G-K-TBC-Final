package com.gabo.gk.ui.model.user

import android.os.Parcelable
import com.gabo.gk.ui.model.product.ProductModelUi
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModelUi(
    val id: String = "",
    val uid: String = "",
    val email: String = "",
    val password: String = "",
    val type: String = "",
    val userName: String = "",
    val profileImg: String = "",
    val contactNumber: String = "",
    val availableAmount: Int = 1000,
    val searchList: List<String> = emptyList(),
    val soldProducts: MutableList<ProductModelUi> = mutableListOf(),
    val purchasedProducts: MutableList<ProductModelUi> = mutableListOf()
): Parcelable