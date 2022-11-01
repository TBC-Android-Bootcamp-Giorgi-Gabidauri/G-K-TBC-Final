package com.gabo.gk.data.repository

import com.gabo.gk.data.global.dataSources.AuthGlobalDataSource
import com.gabo.gk.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authGlobalDataSource: AuthGlobalDataSource
) : AuthRepository {
    override suspend fun logIn(email: String, password: String) = authGlobalDataSource.logIn(email, password)
    override suspend fun register(email: String, password: String) = authGlobalDataSource.register(email, password)
}