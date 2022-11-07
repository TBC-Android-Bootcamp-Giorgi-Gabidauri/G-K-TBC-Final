package com.gabo.gk.domain.model

import android.net.Uri

data class UploadImageModel(
    val filename: String,
    val uid: String,
    val imgUris: List<Uri>?
)