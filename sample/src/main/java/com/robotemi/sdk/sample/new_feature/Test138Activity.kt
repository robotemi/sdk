package com.robotemi.sdk.sample.new_feature

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnRobotReadyListener
import com.robotemi.sdk.listeners.OnZoneEntranceStatusChangedListener
import com.robotemi.sdk.map.Layer
import com.robotemi.sdk.permission.Permission
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
        if (isReady) robot.hideTopBar()
    }

    private fun initTestCases() {
        binding.ibBack.setOnClickListener { finish() }
        binding.btnGetAllZones.setOnClickListener {
            if (robot.checkSelfPermission(Permission.MAP) != Permission.GRANTED) {
                printLog("Map permission not granted")
                return@setOnClickListener
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val zones = robot.getAllZones()
                withContext(Dispatchers.Main) {
                    if (zones.isEmpty()) {
                        printLog("Result: No zones found.")
                    } else {
                        zones.forEachIndexed { index, layer ->
                            printLog("Zone[$index] Full Object: $layer")
                        }
                    }
                }
            }
        }
        binding.btnGetCurrentZones.setOnClickListener {
            if (robot.checkSelfPermission(Permission.MAP) != Permission.GRANTED) {
                printLog("Map permission not granted")
                return@setOnClickListener
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val currentZones = robot.getCurrentZones()
                withContext(Dispatchers.Main) {
                    if (currentZones.isEmpty()) {
                        printLog("Result: No zones found.")
                    } else {
                        currentZones.forEachIndexed { index, layer ->
                            printLog("CurrentZone[$index]: $layer")
                        }
                    }
                }
            }
        }

        binding.btnApplySpeed.setOnClickListener {
            val speed = 0.7f
            robot.setCurrentGoToSpeed(speed)
            printLog("Applied GoTo Speed: $speed (Only effective during active GoTo)")
        }
        binding.btnApplyBypass.setOnClickListener {
            val bypass = true
            robot.setCurrentGoToBypassObstacles(bypass)
            printLog("Applied GoTo Bypass: $bypass (Only effective during active GoTo)")
        }
        binding.btnApplyDistance.setOnClickListener {
            val distanceCm = 5
            robot.setCurrentGoToObstacleAvoidanceDistance(distanceCm)
            printLog("Applied GoTo Avoidance Distance: ${distanceCm}cm (Only effective during active GoTo)")
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