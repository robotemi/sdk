package com.robotemi.sdk.sample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.robotemi.sdk.BatteryData
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnRobotReadyListener

class BootCompleteReceiver: BroadcastReceiver(), OnRobotReadyListener {

    private val sn: String?
        get() = Robot.getInstance().serialNumber

    private val batteryData: BatteryData?
        get() = Robot.getInstance().batteryData

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("BOOT", "BOOT Received")
        Toast.makeText(context!!, "Boot", Toast.LENGTH_LONG).show()

        val snFromBroadcast = intent?.getStringExtra("SN")

        // SdkService is being started in [TemiSdkServiceConnection]
        // SDk interfaces will return null.
        Log.d("BOOT", "SN from SDK $sn, SN from broadcast $snFromBroadcast, battery $batteryData")

        Robot.getInstance().addOnRobotReadyListener(this)
    }

    override fun onRobotReady(isReady: Boolean) {
        if (isReady) {
            Log.d("BOOT", "SN from SDK $sn, battery $batteryData")
            Robot.getInstance().removeOnRobotReadyListener(this)
        }
    }

}