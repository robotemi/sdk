package com.robotemi.sdk.sample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest
import com.robotemi.sdk.constants.SequenceCommand

class TemiBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val ACTION_DEBUG = "temi.debug.sdk"
        private const val ACTION_START_STAND_BY = "temi.debug.standby.start"
        private const val ACTION_STOP_STAND_BY = "temi.debug.standby.stop"
        private const val ACTION_ENABLE_STAND_BY = "temi.debug.standby.enable"
        private const val ACTION_TTS = "temi.debug.tts"
        private const val ACTION_MULTI_FLOOR = "temi.debug.multi.floor"
        private const val ACTION_SEQUENCE = "temi.debug.sequence"
        private const val ACTION_PATROL = "temi.debug.patrol"
        private const val ACTION_MEETING = "temi.debug.meeting"
        private const val ACTION_FACE = "temi.debug.face"
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
                // adb shell am broadcast -a temi.debug.sdk --es action "temi.debug.standby.stop" --es password "1234"
                Log.d("TemiBroadcastReceiver", "stopStandBy, ${Robot.getInstance().isStandByOn()}")
                val password = intent.getStringExtra("password") ?: ""
                val result = Robot.getInstance().stopStandBy(password)
                Log.d("TemiBroadcastReceiver", "stopStandBy, $result")
            }
            ACTION_ENABLE_STAND_BY -> {
                // adb shell am broadcast -a temi.debug.sdk --es action "temi.debug.standby.enable" --ez enable true --es password "1234"
                val enabled = intent.getBooleanExtra("enable", false)
                val password = intent.getStringExtra("password") ?: ""
                Log.d("TemiBroadcastReceiver", "enableStandBy, ${Robot.getInstance().isStandByOn()}, enable $enabled, password $password")
                val result = Robot.getInstance().enableStandBy(enabled, password)
                Log.d("TemiBroadcastReceiver", "enableStandBy, $result")
            }
            ACTION_TTS -> {
                // adb shell am broadcast -a temi.debug.sdk --es action "temi.debug.tts" --ez random true --es language "EN_US" --ez cache true --es text "hello"
                val random = intent.getBooleanExtra("random", false)
                val cache = intent.getBooleanExtra("cache", false)
                val text = intent.getStringExtra("text") ?: ""
                val locale = intent.getStringExtra("language") ?: "SYSTEM"
                val tts = if (random) {
                    if (text.isNotBlank()) {
                        text + System.currentTimeMillis()
                    } else {
                        "adb shell am broadcast -a temi.debug.sdk --es action \"temi.debug.tts\" --ez random true --es language \"us\" --ez cache true --es text \"hello\" " + System.currentTimeMillis()
                    }
                } else {
                    text.ifBlank {
                        "adb shell am broadcast -a temi.debug.sdk --es action \"temi.debug.tts\" --ez random true --es language \"us\" --ez cache true --es text \"hello\""
                    }
                }
                val language =
                    try {
                        TtsRequest.Language.valueOf(locale)
                    } catch (e: Exception) {
                        TtsRequest.Language.SYSTEM
                    }

                Robot.getInstance()
                    .speak(TtsRequest.create(tts, cached = cache, language = language))
            }
            ACTION_MULTI_FLOOR -> {
                // adb shell am broadcast -a temi.debug.sdk --es action "temi.debug.multi.floor" --ez enable true
                val enable = intent.getBooleanExtra("enable", false)
                val isEnabled = Robot.getInstance().isMultiFloorEnabled()
                Log.d("TemiBroadcastReceiver", "MultiFloor isEnabled $isEnabled")
                val ret = Robot.getInstance().setMultiFloorEnabled(enable)
                Log.d(
                    "TemiBroadcastReceiver",
                    "MultiFloor setMultiFloorEnabled $enable, result $ret"
                )
            }
            ACTION_SEQUENCE -> {
                // adb shell am broadcast -a temi.debug.sdk --es action "temi.debug.sequence" --es control "pause|play|step_forward|step_backward"
                val command = intent.getStringExtra("control")
                val robot = Robot.getInstance()
                when {
                    SequenceCommand.STOP.name.equals(command, true) -> {
                        robot.controlSequence(SequenceCommand.STOP)
                    }
                    SequenceCommand.PAUSE.name.equals(command, true) -> {
                        robot.controlSequence(SequenceCommand.PAUSE)
                    }
                    SequenceCommand.PLAY.name.equals(command, true) -> {
                        robot.controlSequence(SequenceCommand.PLAY)
                    }
                    SequenceCommand.STEP_BACKWARD.name.equals(command, true) -> {
                        robot.controlSequence(SequenceCommand.STEP_BACKWARD)
                    }
                    SequenceCommand.STEP_FORWARD.name.equals(command, true) -> {
                        robot.controlSequence(SequenceCommand.STEP_FORWARD)
                    }
                }
            }
            ACTION_PATROL -> {
                // adb shell am broadcast -a temi.debug.sdk --es action "temi.debug.patrol" --esa locations 1,2,3 --ez nonstop false --ei times 0 --ei waiting 4
                val locations = intent.getStringArrayExtra("locations")?.toList() ?: emptyList()
                val nonstop = intent.getBooleanExtra("nonstop", false)
                val times = intent.getIntExtra("times", 0)
                val waiting = intent.getIntExtra("waiting", 3)
                Robot.getInstance().patrol(locations, nonstop, times, waiting)

            }
            ACTION_MEETING -> {
                //adb shell am broadcast -a temi.debug.sdk --es action "temi.debug.meeting" --es control "stop"
                val command = intent.getStringExtra("control")
                when (command) {
                    "stop" -> Robot.getInstance().stopTelepresence()
                }
            }
            ACTION_FACE -> {
                //adb shell am broadcast -a temi.debug.sdk --es action "temi.debug.face" --ez start true --ez withSdkFaces true
                val start = intent.getBooleanExtra("start", false)
                val withSdkFaces = intent.getBooleanExtra("withSdkFaces", false)
                if (start) {
                    Robot.getInstance().startFaceRecognition(withSdkFaces = withSdkFaces)
                } else {
                    Robot.getInstance().stopFaceRecognition()
                }
            }
        }
    }
}