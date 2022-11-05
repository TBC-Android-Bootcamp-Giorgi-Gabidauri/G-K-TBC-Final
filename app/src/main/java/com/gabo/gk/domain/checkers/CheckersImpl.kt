package com.gabo.gk.domain.checkers

import android.content.Context
import android.util.Patterns
import com.gabo.gk.R
import com.gabo.gk.domain.model.UserModelDomain
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CheckersImpl @Inject constructor(@ApplicationContext private val context: Context) :
    Checkers {
    override fun checkRepeatedPassword(pass: String, repeatedPass: String): String {
        return when {
            pass.length < 6 -> context.getString(R.string.password_must_be_more_than_6)
            pass.isNotEmpty() && repeatedPass.isNotEmpty() && pass == repeatedPass -> ""
            else -> {
                (context.getString(R.string.passwords_dont_match))
            }
        }
    }

    override fun checkEmailFormat(email: String): Boolean {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    override fun checkEmptyFields(userModel: UserModelDomain): String {
        with(userModel) {
            return when {
                email.isEmpty() -> context.getString(R.string.email_empty_error)
                password.isEmpty() -> context.getString(R.string.password_empty_error)
                userName.isEmpty() -> context.getString(R.string.username_empty_error)
                else -> ""
            }
        }
    }
    override fun checkIfUserNameEmpty(userModel: UserModelDomain): String{
        with(userModel) {
            return when {
               userName.isEmpty() -> context.getString(R.string.username_empty_error)
                else -> ""
            }
        }
    }
}