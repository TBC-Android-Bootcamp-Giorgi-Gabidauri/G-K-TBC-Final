package com.gabo.gk.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationModel(
    val imgClient: String = "",
    val info: String = "",
    val imgProduct: String = ""
): Parcelable