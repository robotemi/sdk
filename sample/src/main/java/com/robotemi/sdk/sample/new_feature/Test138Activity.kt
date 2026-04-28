package com.robotemi.sdk.sample.new_feature

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnRobotReadyListener
import com.robotemi.sdk.listeners.OnZoneEntranceStatusChangedListener
import com.robotemi.sdk.map.Layer
import com.robotemi.sdk.sample.databinding.ActivityTest138Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Test138Activity : AppCompatActivity(), OnRobotReadyListener,
    OnZoneEntranceStatusChangedListener {

    private lateinit var binding: ActivityTest138Binding
    private val robot = Robot.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTest138Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initTestCases()
    }

    override fun onStart() {
        super.onStart()
        robot.addOnRobotReadyListener(this)
        robot.addOnZoneEntranceStatusChangedListener(this)
    }

    override fun onStop() {
        super.onStop()
        robot.removeOnRobotReadyListener(this)
        robot.removeOnZoneEntranceStatusChangedListener(this)
    }

    override fun onRobotReady(isReady: Boolean) {
        if (isReady) {
            printLog("Robot is ready")
        }
    }

    private fun initTestCases() {
        binding.ibBack.setOnClickListener { finish() }
        binding.btnGetAllZones.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val zones = robot.getAllZones()
                printLog("--- All Zones Count: ${zones.size} ---")
                withContext(Dispatchers.Main) {
                    zones.forEachIndexed { index, layer ->
                        printLog( "Zone[$index] Full Object: $layer")
                    }
                }
            }
        }
        binding.btnGetCurrentZones.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val currentZones = robot.getCurrentZones()
                withContext(Dispatchers.Main) {
                    printLog("--- Current Zones Count: ${currentZones.size} ---")
                    if (currentZones.isEmpty()) {
                        printLog("Result: None")
                    } else {
                        currentZones.forEachIndexed { index, layer ->
                            printLog("CurrentZone[$index]: $layer")
                        }
                    }
                }
            }
        }

        binding.btnApplySpeed.setOnClickListener {
            //0.3f ~ 1.2f
            val speed = 0.7f
            robot.setCurrentGoToSpeed(speed)
            printLog("Applied GoTo Speed: $speed (Only effective during active GoTo)")
        }
        binding.btnApplyBypass.setOnClickListener {
            val bypass = true
            robot.setCurrentGoToBypassObstacles(bypass)
            printLog("Applied GoTo Bypass: $bypass")
        }
        binding.btnApplyDistance.setOnClickListener {
            //5cm
            val distanceMm = 5
            robot.setCurrentGoToObstacleAvoidanceDistance(distanceMm)
            printLog("Applied GoTo Avoidance Distance: ${distanceMm}cm")
        }

        binding.btnClearLog.setOnClickListener { clearLog() }
    }


    override fun onZoneEntranceStatusChanged(layers: List<Layer>) {

        printLog("--- Zone Entrance Event ---")
        if (layers.isEmpty()) {
            printLog("Current Status: Outside of all zones")
        } else {
            layers.forEachIndexed { index, layer ->
                printLog("Layer[$index]: $layer")
            }
        }
    }

    private fun printLog(msg: String) {
        Log.d("Test138Activity", msg)
        runOnUiThread {
            val currentText = binding.tvLog.text.toString()
            binding.tvLog.text = "${System.currentTimeMillis()}: $msg\n$currentText"
        }
    }

    private fun clearLog() {
        binding.tvLog.text = ""
    }
}