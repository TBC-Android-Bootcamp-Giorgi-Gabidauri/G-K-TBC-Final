package com.gabo.gk.comon.extensions

import android.widget.TextView

fun TextView.txt(): String {
    return this.text.toString()
}