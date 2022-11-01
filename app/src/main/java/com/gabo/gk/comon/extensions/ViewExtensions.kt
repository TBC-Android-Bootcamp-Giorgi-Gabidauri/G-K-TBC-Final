package com.gabo.gk.comon.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackBar(txt:String, view: View = this){
    val snack = Snackbar.make(view,txt,Snackbar.LENGTH_SHORT)
    snack.show()
}