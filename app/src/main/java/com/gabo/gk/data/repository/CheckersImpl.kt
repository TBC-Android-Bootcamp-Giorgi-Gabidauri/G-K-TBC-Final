package com.gabo.gk.data.repository

import com.gabo.gk.comon.checkers.Checkers
import javax.inject.Inject

class CheckersImpl @Inject constructor(): Checkers {
    override fun checkRepeatedPassword(pass: String, repeatedPass: String): String {
        return if(pass.isNotEmpty() && repeatedPass.isNotEmpty() && pass == repeatedPass){
           ("welcome")
        }else{
            ("password do not match")
        }
    }
}