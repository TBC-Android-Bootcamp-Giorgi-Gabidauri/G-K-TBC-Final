package com.gabo.gk.domain.useCases.checkers

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.checkers.Checkers
import javax.inject.Inject

class CheckEmailFormatUseCase @Inject constructor(private val checkers: Checkers) :
    BaseUseCase<String, String> {
    override suspend fun invoke(params: String): String {
        return if (!checkers.checkEmailFormat(params)) {
            "email formatted badly"
        } else ""
    }
}