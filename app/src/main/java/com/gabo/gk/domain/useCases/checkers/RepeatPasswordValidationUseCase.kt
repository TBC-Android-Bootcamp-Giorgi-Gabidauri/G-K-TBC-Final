package com.gabo.gk.domain.useCases.checkers

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.checkers.Checkers
import javax.inject.Inject

class RepeatPasswordValidationUseCase @Inject constructor(private val checkers: Checkers) :
    BaseUseCase<Pair<String, String>, String> {
    override suspend fun invoke(params: Pair<String, String>): String {
        return checkers.checkRepeatedPassword(params.first, params.second)
    }
}

