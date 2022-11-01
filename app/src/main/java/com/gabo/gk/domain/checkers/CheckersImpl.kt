package com.gabo.gk.domain.checkers

import android.content.Context
import com.gabo.gk.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CheckersImpl @Inject constructor(@ApplicationContext private val context: Context): Checkers {
    override fun checkRepeatedPassword(pass: String, repeatedPass: String): String {
        return if(pass.isNotEmpty() && repeatedPass.isNotEmpty() && pass == repeatedPass){
           (context.getString(R.string.welcome))
        }else{
            (context.getString(R.string.passwords_dont_match))
        }
    }
}