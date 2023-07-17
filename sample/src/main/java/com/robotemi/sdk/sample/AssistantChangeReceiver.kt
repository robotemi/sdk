package com.robotemi.sdk.sample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.robotemi.sdk.BatteryData
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnRobotReadyListener


/**
 * To receive this broadcast. You have to declare the use of permission
 *
 * <uses-permission android:name="com.robotemi.permission.ASSISTANT"/>
 *
 * in you manifest.
 */
class AssistantChangeReceiver: BroadcastReceiver() {

    companion object {
        const val ACTION_ASSISTANT_SELECTION = "com.robotemi.ASSISTANT_SELECTION"
        private const val KEY_ASSISTANT = "assistant"
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        val assistant = intent?.getStringExtra(KEY_ASSISTANT) ?: ""

        Log.d("ASSISTANT", "Assistant Changed, $assistant")

        Toast.makeText(context!!, "Assistant $assistant", Toast.LENGTH_LONG).show()
    }
}