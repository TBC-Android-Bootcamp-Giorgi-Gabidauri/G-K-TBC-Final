package com.gabo.gk.comon.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gabo.gk.R

fun ImageView.loadImage(imgUrl: Any) {
    Glide.with(this).load(imgUrl).placeholder(R.drawable.ic_place_holder).into(this)
}

fun ImageView.loadImageDecreasedQuality(img: Any) {
    Glide.with(this).load(img).placeholder(R.drawable.ic_place_holder)
        .apply(
            RequestOptions()
                .override(350, 240)
        )
        .into(this)
}