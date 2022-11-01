package com.gabo.gk.domain.checkers

interface Checkers {
    fun checkRepeatedPassword(pass: String, repeatedPass: String):String
}