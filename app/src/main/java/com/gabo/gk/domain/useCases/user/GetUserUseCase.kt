package com.gabo.gk.domain.useCases.user

import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.UserModelDomain
import com.gabo.gk.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) :
    BaseUseCase<String, Resource<UserModelDomain>> {
    override suspend fun invoke(params: String): Resource<UserModelDomain> {
        return userRepository.getUser(params)
    }
}