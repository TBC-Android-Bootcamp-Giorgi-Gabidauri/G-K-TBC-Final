package com.gabo.gk.domain.repository

import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.UserModelDomain

interface UserRepository {
    suspend fun createUser(user: UserModelDomain): String
    suspend fun getUser(uid: String): Resource<UserModelDomain>
    suspend fun updateUser(user: UserModelDomain): String
}