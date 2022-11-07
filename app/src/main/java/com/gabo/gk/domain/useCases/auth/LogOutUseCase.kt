package com.gabo.gk.domain.useCases.auth

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.repository.AuthRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val authRepository: AuthRepository) :
    BaseUseCase<Unit, Unit> {
    override suspend fun invoke(params: Unit) {
        authRepository.logOut()
    }
}