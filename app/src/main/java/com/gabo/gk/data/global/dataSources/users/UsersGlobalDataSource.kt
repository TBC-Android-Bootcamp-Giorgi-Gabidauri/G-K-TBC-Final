package com.gabo.gk.data.global.dataSources.users

import com.gabo.gk.comon.response.Resource
import com.gabo.gk.data.global.dto.UserDto
import com.gabo.gk.domain.model.UserModelDomain

interface UsersGlobalDataSource {
    suspend fun createUser(user: UserDto): String
    suspend fun getUser(uid: String): Resource<UserModelDomain>
    suspend fun updateUser(user: UserDto): String
}