package com.gabo.gk.notification.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log.d
import androidx.core.app.NotificationCompat
import com.gabo.gk.R
import com.gabo.gk.comon.constants.TOKEN
import com.gabo.gk.comon.response.Resource
import com.gabo.gk.domain.model.UserModelDomain
import com.gabo.gk.domain.useCases.user.GetUserUseCase
import com.gabo.gk.domain.useCases.user.UpdateUserUseCase
import com.gabo.gk.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

private const val CHANNEL_ID = "my_channel"

class FirebaseService : FirebaseMessagingService() {
    @Inject
    lateinit var updateUserUseCase: UpdateUserUseCase

    @Inject
    lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var getUserUseCase: GetUserUseCase

    @Inject
    lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "updateTokenError"
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        CoroutineScope(Dispatchers.IO).launch {
            var user: UserModelDomain? = null
            when (val result = getUserUseCase(auth.currentUser!!.uid)) {
                is Resource.Success -> user = result.data!!
                is Resource.Error -> {}
            }
            if (user != null) {
                user = user.copy(token = newToken)
                updateUserUseCase(user)
            } else {
                d(TAG, TAG)
            }
        }
        sharedPref.edit()?.putString(TOKEN, newToken)?.apply()

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val intent = Intent(this, MainActivity::class.java).putExtra("notification",message.data["title"])
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        createNotificationChannel(notificationManager)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationID, notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {
            description = "My channel description"
            enableLights(true)
            lightColor = Color.GREEN
        }
        notificationManager.createNotificationChannel(channel)
    }
}
