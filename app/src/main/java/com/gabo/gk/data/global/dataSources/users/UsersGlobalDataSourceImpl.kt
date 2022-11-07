package com.gabo.gk.data.global.dataSources.users

import android.content.Context
import com.gabo.gk.R
import com.gabo.gk.comon.constants.FIELD_UID
import com.gabo.gk.comon.constants.Users_Storage
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.data.global.dto.UserDto
import com.gabo.gk.data.transformers.toDomain
import com.gabo.gk.data.transformers.toDto
import com.gabo.gk.domain.model.UserModelDomain
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersGlobalDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    @ApplicationContext private val context: Context,
) : UsersGlobalDataSource{
    override suspend fun createUser(user: UserDto) = try {
        fireStore.collection(Users_Storage).add(user).await()
        (context.getString(R.string.registered_successfully))
    } catch (e: Exception) {
        (e.message.toString())
    }

    override suspend fun getUser(uid: String): Resource<UserModelDomain> {
        return try {
            var userResult: UserModelDomain? = null
            val result =
                fireStore.collection(Users_Storage).whereEqualTo(FIELD_UID, uid).get().await()
            result?.documents?.forEach {
                var user = it.toObject<UserDto>()
                user = user?.copy(id = it.id)
                user?.let { dto -> userResult = dto.toDomain() }
            }
            if (userResult != null) {
                (Resource.Success(userResult))
            } else {
                (Resource.Error(context.getString(R.string.could_not_find_user)))
            }
        } catch (e: Exception) {
            (Resource.Error(e.message))
        }
    }

    override suspend fun updateUser(user: UserDto): String {
        return try {
            var userResult: UserDto? = null
            when (val result = getUser(user.uid)) {
                is Resource.Success -> {
                    userResult = result.data!!.toDto()
                }
                is Resource.Error -> {
                    (context.getString(R.string.could_not_find_user))
                }
            }
            if (userResult != null) {
                fireStore.collection(Users_Storage).document(userResult.id).set(user).await()
                (context.getString(R.string.user_updated_successfully))
            } else {
                (context.getString(R.string.user_is_not_exist))
            }
        } catch (e: Exception) {
            (e.message.toString())
        }
    }
}