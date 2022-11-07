package com.gabo.gk.data.global.dataSources.auth

import android.content.Context
import com.gabo.gk.R
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthGlobalDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @ApplicationContext private val context: Context
): AuthGlobalDataSource {

    override suspend fun logIn(email: String, password: String) = flow {
        try { auth.signInWithEmailAndPassword(email, password).await()
            if (auth.currentUser == null) emit(context.getString(R.string.something_went_wrong))
            else emit(context.getString(R.string.logged_in_successfully))
        } catch (e: Exception) { emit(e.message.toString()) }
    }

    override suspend fun register(email: String, password: String) = flow {
        try { auth.createUserWithEmailAndPassword(email, password).await()
            if (auth.currentUser == null) emit(context.getString(R.string.something_went_wrong))
            else emit(context.getString(R.string.registered_successfully))
        } catch (e: Exception) { emit(e.message.toString()) }
    }

    override fun logOut(){
        auth.signOut()
    }
}