package com.gabo.gk.ui.model.addImage

import android.net.Uri
import com.gabo.gk.R

data class ImageModel(
    val image: Uri? = null,
    val defaultImg: Int = R.drawable.ic_add_photo
)
