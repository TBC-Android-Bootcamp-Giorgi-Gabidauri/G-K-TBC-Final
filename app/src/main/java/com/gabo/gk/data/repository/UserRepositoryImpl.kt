package com.gabo.gk.data.repository

import com.gabo.gk.comon.response.Resource
import com.gabo.gk.data.global.dataSources.UsersGlobalDataSource
import com.gabo.gk.data.transformers.toDto
import com.gabo.gk.domain.model.UserModelDomain
import com.gabo.gk.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val usersGlobalDataSource: UsersGlobalDataSource) :
    UserRepository {
    override suspend fun createUser(user: UserModelDomain): String {
        return usersGlobalDataSource.createUser(user.toDto())
    }

    override suspend fun getUser(uid: String): Resource<UserModelDomain> =
        usersGlobalDataSource.getUser(uid)

    override suspend fun updateUser(user: UserModelDomain) =
        usersGlobalDataSource.updateUser(user.toDto())
}