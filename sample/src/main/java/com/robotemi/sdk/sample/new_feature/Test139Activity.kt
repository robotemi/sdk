package com.robotemi.sdk.sample.new_feature

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnRobotReadyListener
import com.robotemi.sdk.listeners.OnSerialRawDataListener
import com.robotemi.sdk.sample.databinding.ActivityTest139Binding
import com.robotemi.sdk.serial.Serial
import com.robotemi.sdk.serial.Serial.dataHex
import kotlin.collections.plus

class Test139Activity : AppCompatActivity(), OnRobotReadyListener, OnSerialRawDataListener {

    private lateinit var binding: ActivityTest139Binding
    private val robot = Robot.getInstance()
    private var direction = RIGHT_TO_LEFT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTest139Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initTestCases()
    }

    override fun onStart() {
        super.onStart()
        robot.addOnRobotReadyListener(this)
        robot.addOnSerialRawDataListener(this)
    }

    override fun onStop() {
        super.onStop()
        robot.removeOnRobotReadyListener(this)
        robot.removeOnSerialRawDataListener(this)
    }

    override fun onRobotReady(isReady: Boolean) {
        if (isReady) robot.hideTopBar()
    }

    private fun initTestCases() {
        initScrollConfig()
        binding.ibBack.setOnClickListener { finish() }
        binding.btnApplyNormalText.setOnClickListener { applyNormalText() }
        binding.btnApplyScrollText.setOnClickListener { applyScrollText() }
        binding.btnApplyScrollSpeed.setOnClickListener { applyScrollSpeed() }
        binding.btnStartScrollText.setOnClickListener { setScrollingEnabled(true) }
        binding.btnStopScrollText.setOnClickListener { setScrollingEnabled(false) }
        binding.btnLeftToRight.setOnClickListener {
            direction = LEFT_TO_RIGHT
            setScrollDirection()
        }
        binding.btnRightToLeft.setOnClickListener {
            direction = RIGHT_TO_LEFT
            setScrollDirection()
        }
        binding.btnTopToBottom.setOnClickListener {
            direction = TOP_TO_BOTTOM
            setScrollDirection()
        }
        binding.btnBottomToTop.setOnClickListener {
            direction = BOTTOM_TO_TOP
            setScrollDirection()
        }
        binding.btnClearLog.setOnClickListener { clearLog() }
    }

    private fun initScrollConfig() {
        binding.etScrollInterval.setText(Serial.LCD.SCROLL_INTERVAL_DEFAULT.toString())
        binding.etScrollDistance.setText(Serial.LCD.SCROLL_DISTANCE_DEFAULT.toString())
    }

    private fun applyNormalText() {
        binding.etScrollText.setText(NORMAL_TEXT)
        sendSingleCommand(Serial.getLcdNormalTextBytes(NORMAL_TEXT) + Serial.getLcdPersistBytes(true), "Apply normal text")
    }

    private fun applyScrollText() {
        // The simplest implementation of scrolling text and Persist
    /*    sendSingleCommand(
            Serial.getLcdScrollTextBytes(SCROLL_TEXT) + Serial.getLcdPersistBytes(true),
            "Apply scroll text"
        )*/

        // Customize the scrolling direction, speed and Persist
        val interval = scrollInterval()
        val distance = scrollDistance()
        binding.etScrollText.setText(SCROLL_TEXT)
        sendSingleCommand(
            Serial.getLcdScrollTextBytes(SCROLL_TEXT, direction = direction, interval = interval, distance = distance) + Serial.getLcdPersistBytes(true),
            "Apply scroll text interval=$interval,distance=$distance"
        )
    }

    private fun applyScrollSpeed() {
        val interval = scrollInterval()
        val distance = scrollDistance()
        sendSingleCommand(
            Serial.getLcdScrollSpeedBytes(interval = interval, distance = distance),
            "Apply scroll speed interval=$interval,distance=$distance"
        )
    }

    private fun setScrollingEnabled(enabled: Boolean) {
        sendSingleCommand(
            Serial.getLcdScrollEnabledBytes(enabled),
            "Scroll text ${if (enabled) "start" else "stop"}"
        )
    }

    private fun setScrollDirection() {
        sendSingleCommand(
            Serial.getLcdScrollDirectionBytes(direction),
            "Scroll direction=${directionLabel()}"
        )
    }

    private fun sendSingleCommand(command: ByteArray, label: String) {
        val result = robot.sendSerialCommand(Serial.CMD_LCD_TEXT, command)
        val status = if (result == 0) "Success" else "Failed"
        printLog("$label -> $status ($result), data=${command.dataHex}")
    }

    private fun directionLabel(): String {
        return when (direction) {
            LEFT_TO_RIGHT -> "left_to_right"
            TOP_TO_BOTTOM -> "top_to_bottom"
            BOTTOM_TO_TOP -> "bottom_to_top"
            else -> "right_to_left"
        }
    }

    private fun scrollInterval(): Int {
        val interval = binding.etScrollInterval.text.toString()
            .toIntOrNull()
            ?.coerceAtLeast(Serial.LCD.SCROLL_INTERVAL_MIN)
            ?: Serial.LCD.SCROLL_INTERVAL_DEFAULT
        binding.etScrollInterval.setText(interval.toString())
        return interval
    }

    private fun scrollDistance(): Int {
        val distance = binding.etScrollDistance.text.toString()
            .toIntOrNull()
            ?.coerceIn(Serial.LCD.SCROLL_DISTANCE_MIN, Serial.LCD.SCROLL_DISTANCE_MAX)
            ?: Serial.LCD.SCROLL_DISTANCE_DEFAULT
        binding.etScrollDistance.setText(distance.toString())
        return distance
    }

    override fun onSerialRawData(data: ByteArray) {
        printLog("Serial raw data: ${data.dataHex}")
    }

    private fun printLog(msg: String, show: Boolean = true) {
        printLog("", msg, show)
    }

    private fun printLog(tag: String, msg: String, show: Boolean = true) {
        Log.d(tag.ifEmpty { "Test139Activity" }, msg)
        if (!show) return
        runOnUiThread {
            binding.tvLog.gravity = Gravity.BOTTOM
            binding.tvLog.append("- $msg \n")
        }
    }

    private fun clearLog() {
        binding.tvLog.text = ""
    }

    companion object {
        private const val LEFT_TO_RIGHT = Serial.LCD.SCROLL_DIRECTION_LEFT_TO_RIGHT
        private const val RIGHT_TO_LEFT = Serial.LCD.SCROLL_DIRECTION_RIGHT_TO_LEFT
        private const val TOP_TO_BOTTOM = Serial.LCD.SCROLL_DIRECTION_TOP_TO_BOTTOM
        private const val BOTTOM_TO_TOP = Serial.LCD.SCROLL_DIRECTION_BOTTOM_TO_TOP
        private const val NORMAL_TEXT = "Normal text"
        private const val SCROLL_TEXT = "Scrolling text demo 139"
    }
}
