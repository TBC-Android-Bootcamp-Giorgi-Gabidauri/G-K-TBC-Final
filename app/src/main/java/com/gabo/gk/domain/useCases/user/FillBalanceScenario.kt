package com.gabo.gk.domain.useCases.user

import android.content.Context
import com.gabo.gk.R
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.UserModelDomain
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FillBalanceScenario @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val auth: FirebaseAuth,
    @ApplicationContext private val context: Context
) : BaseUseCase<Int, String> {
    override suspend fun invoke(params: Int): String {
        try {
            if (params != 0){
                var user: UserModelDomain? = null
                when (val result = getUserUseCase(auth.currentUser!!.uid)) {
                    is Resource.Success -> user = result.data
                    is Resource.Error -> return result.errorMsg!!
                }
                if (user != null) user.let {
                    it.availableAmount = it.availableAmount + params
                    updateUserUseCase(it)
                    return context.getString(R.string.balance_filled_successfully)
                } else {
                    return context.getString(R.string.something_went_wrong)
                }
            }else{
                return context.getString(R.string.amount_must_be_more_than_0)
            }
        } catch (e: Exception) {
            return e.message.toString()
        }
    }
}