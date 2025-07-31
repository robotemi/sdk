package com.robotemi.sdk.sample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * A static receiver that listens for broadcast events. Can also use dynamic registration as shown in MainActivity
 * The broadcast is sent by action + package name.
 * Note that class name doesn't matter.
 *
 */
class OtaStaticReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("SdkSampleReceiver", "Static Receiver received action:  ${intent?.action}," +
                " ${intent?.getStringExtra("type")}," +
                " ${intent?.getStringExtra("status")}," +
                " ${intent?.getStringExtra("details")}")
    }
}