package com.gabo.gk.domain.useCases.checkers

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.checkers.Checkers
import com.gabo.gk.domain.model.UserModelDomain
import javax.inject.Inject

class CheckIfUserNameEmptyUseCase @Inject constructor(private val checkers: Checkers) :
    BaseUseCase<UserModelDomain, String> {
    override suspend fun invoke(params: UserModelDomain): String {
        return checkers.checkIfUserNameEmpty(params)
    }
}