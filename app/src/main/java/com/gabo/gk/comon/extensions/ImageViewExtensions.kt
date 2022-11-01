package com.gabo.gk.comon.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(imgUrl: Any) {
    Glide.with(this).load(imgUrl).into(this)
}

fun ImageView.loadImageDecreasedQuality(img: Any) {
    Glide.with(this).load(img)
        .apply(
            RequestOptions()
                .override(350, 240)
        )
        .into(this)
}