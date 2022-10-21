package com.gabo.gk.data.repository

import com.gabo.gk.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun logIn(email: String, password: String) = flow {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .await()
            if (auth.currentUser == null) {
                emit("something went wrong")
            } else {
                emit("LoggedIn Successfully")
            }
        } catch (e: Exception) {
            emit(e.message.toString())
        }
    }

    override suspend fun register(email: String, password: String) = flow {
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .await()
            if (auth.currentUser == null) {
                emit("something went wrong")
            } else {
                emit("Registered Successfully")
            }
        } catch (e: Exception) {
            emit(e.message.toString())
        }
    }
}