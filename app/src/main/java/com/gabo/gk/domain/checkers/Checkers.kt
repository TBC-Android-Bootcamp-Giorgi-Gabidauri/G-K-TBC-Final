package com.gabo.gk.domain.checkers

import com.gabo.gk.domain.model.UserModelDomain

interface Checkers {
    fun checkRepeatedPassword(pass: String, repeatedPass: String):String
    fun checkEmailFormat(email: String): Boolean
    fun checkEmptyFields(userModel: UserModelDomain): String
    fun checkIfUserNameEmpty(userModel: UserModelDomain): String
}