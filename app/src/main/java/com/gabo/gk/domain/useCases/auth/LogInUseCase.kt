package com.gabo.gk.domain.useCases.auth

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val repository: AuthRepository
): BaseUseCase<Pair<String, String>, Flow<String>> {
    override suspend fun invoke(params: Pair<String, String>): Flow<String> {
        return  repository.logIn(params.first, params.second)
    }
}