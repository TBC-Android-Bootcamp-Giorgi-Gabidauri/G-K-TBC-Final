package com.gabo.gk.domain.useCases.product

import android.content.Context
import com.gabo.gk.R
import com.gabo.gk.base.BaseUseCase
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.NotificationModel
import com.gabo.gk.domain.useCases.user.GetUserUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val auth: FirebaseAuth,
    @ApplicationContext private val context: Context
) : BaseUseCase<Unit, Resource<List<NotificationModel>>> {
    override suspend fun invoke(params: Unit): Resource<List<NotificationModel>> {
        return try {
            var notifications: List<NotificationModel> = emptyList()
            when (val user = getUserUseCase(auth.currentUser!!.uid)) {
                is Resource.Success -> notifications =
                    user.data?.notifications ?: emptyList()
                is Resource.Error -> return Resource.Error(user.errorMsg!!)
            }
            if (notifications.isNotEmpty()) {
                Resource.Success(notifications)
            } else {
                Resource.Error(context.getString(R.string.something_went_wrong))
            }
        } catch (e: Error) {
            Resource.Error(e.message)
        }
    }
}