package com.robotemi.sdk.sample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest

class TemiBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val ACTION_DEBUG = "temi.debug.sdk"
        private const val ACTION_START_STAND_BY = "temi.debug.standby.start"
        private const val ACTION_STOP_STAND_BY = "temi.debug.standby.stop"
        private const val ACTION_TTS = "temi.debug.tts"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("TemiBroadcastReceiver", "Broadcast received")
        if (context == null || intent == null) {
            return
        }

        when (intent.getStringExtra("action") ?: "") {
            ACTION_START_STAND_BY -> {
                // adb shell am broadcast -a temi.debug.sdk --es action "temi.debug.standby.start"
                Log.d("TemiBroadcastReceiver", "startStandBy, ${Robot.getInstance().isStandByOn()}")
                val result = Robot.getInstance().startStandBy()
                Log.d("TemiBroadcastReceiver", "startStandBy, $result")
            }
            ACTION_STOP_STAND_BY -> {
                // adb shell am broadcast -a temi.debug.sdk --es action "temi.debug.standby.start" --es password "1234"
                Log.d("TemiBroadcastReceiver", "stopStandBy, ${Robot.getInstance().isStandByOn()}")
                val password = intent.getStringExtra("password") ?: ""
                val result = Robot.getInstance().stopStandBy(password)
                Log.d("TemiBroadcastReceiver", "stopStandBy, $result")
            }
            ACTION_TTS -> {
                // adb shell am broadcast -a temi.debug.sdk --es action "temi.debug.tts" --es random "ts"
                val random = intent.getStringExtra("random") ?: ""
                if (random == "ts") {
                    Robot.getInstance().speak(TtsRequest.create("Hello ${System.currentTimeMillis()}"))
                } else {
                    Robot.getInstance().speak(TtsRequest.create("Hello"))
                }
            }
        }
    }
}