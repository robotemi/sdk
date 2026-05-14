package com.robotemi.sdk.sample.new_feature

import android.os.Bundle
import android.util.Log
import android.view.Gravity
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
        robot.removeOnZoneEntranceStatusChangedListener(this)
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
                val allZones = robot.getAllZones()
                withContext(Dispatchers.Main) {
                    printLog("allZones = $allZones")
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
                    printLog("currentZones = $currentZones")
                }
            }
        }

        binding.btnApplySpeed.setOnClickListener {
            val speed = 0.7f
            val result = robot.setCurrentGoToSpeed(speed)
            if (result != 200) {
                printLog("Apply Speed failed: $result (Ensure robot is in GoTo session)")
            } else {
                printLog("Applied GoTo Speed: $speed (Success)")
            }
        }
        binding.btnApplyBypass.setOnClickListener {
            val bypass = true
            val result = robot.setCurrentGoToBypassObstacles(bypass)
            if (result != 200) {
                printLog("Apply Bypass failed: $result (Ensure robot is in GoTo session)")
            } else {
                printLog("Applied GoTo Bypass: $bypass (Success)")
            }
        }
        binding.btnApplyDistance.setOnClickListener {
            val distanceCm = 5
            val result = robot.setCurrentGoToObstacleAvoidanceDistance(distanceCm)
            if (result != 200) {
                printLog("Apply Distance failed: $result (Ensure robot is in GoTo session)")
            } else {
                printLog("Applied GoTo Avoidance Distance: ${distanceCm}cm (Success)")
            }
        }

        binding.btnClearLog.setOnClickListener { clearLog() }
    }


    override fun onZoneEntranceStatusChanged(layers: List<Layer>) {
        printLog("onZoneEntranceStatusChanged()  --> layers=$layers")
    }

    private fun printLog(msg: String, show: Boolean = true) {
        printLog("", msg, show)
    }

    private fun printLog(tag: String, msg: String, show: Boolean = true) {
        Log.d(tag.ifEmpty { "Test138Activity" }, msg)
        if (!show) return
        runOnUiThread {
            binding.tvLog.gravity = Gravity.BOTTOM
            binding.tvLog.append("· $msg \n")
        }
    }

    private fun clearLog() {
        binding.tvLog.text = ""
    }
}