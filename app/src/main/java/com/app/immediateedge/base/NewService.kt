package com.app.immediateedge.base

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.app.immediateedge.presentation.LoadingActivity


class NewService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sendMessageToActivity()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun load(): Boolean {
        fun isNotADB(): Boolean =
            Settings.Global.getString(
                applicationContext.contentResolver,
                Settings.Global.ADB_ENABLED
            ) != "1"
        return isNotADB()
    }

    private fun sendMessageToActivity() {
        val serviceIntent = Intent(LoadingActivity.BROADCAST_RECEIVER_ACTION)
        val abdCheck = load()

        Log.d("Server", "result = $abdCheck")

        serviceIntent.putExtra(LoadingActivity.BROADCAST_RECEIVER_KEY, abdCheck)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(serviceIntent)

    }
}