package com.gabo.gk.domain.useCases.user

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.domain.model.UserModelDomain
import com.gabo.gk.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(private val userRepository: UserRepository) :
    BaseUseCase<UserModelDomain, String> {
    override suspend fun invoke(params: UserModelDomain): String {
        return userRepository.updateUser(params)
    }
}