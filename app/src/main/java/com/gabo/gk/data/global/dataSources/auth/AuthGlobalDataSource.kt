package com.gabo.gk.data.global.dataSources.auth

import kotlinx.coroutines.flow.Flow

interface AuthGlobalDataSource {
    suspend fun logIn(email: String, password: String): Flow<String>
    suspend fun register(email: String, password: String): Flow<String>
    fun logOut()
}