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
        binding.ibBack.setOnClickListener { finish() }
        binding.btnApplyDefaultLcdText.setOnClickListener { applyDefaultLcdText() }
        binding.btnApplyCustomScrollText.setOnClickListener { applyCustomScrollText() }
        binding.btnClearLog.setOnClickListener { clearLog() }
    }

    private fun applyDefaultLcdText() {
        // Simple use case
//        val command = Serial.getLcdBytes(SCROLL_TEXT) + Serial.getLcdPersistBytes(true)

        // If you want to set the LCD text color and LCD background color, you can refer to the following code
        val lcdTextColor = byteArrayOf(0xFF.toByte(), 0x00, 0x00)
        val lcdBackgroundColor = byteArrayOf(0x00, 0xFF.toByte(), 0x00)
        val command = Serial.getLcdBytes(SCROLL_TEXT) +
                Serial.getLcdColorBytes(
                    lcdTextColor, target = Serial.LCD.SCROLL_TEXT_COLOR
                ) +
                Serial.getLcdColorBytes(
                    lcdBackgroundColor, target = Serial.LCD.SCROLL_TEXT_BACKGROUND
                ) +
                Serial.getLcdPersistBytes(true)

        sendLcdCommand(command, "Apply default LCD text")
    }

    private fun applyCustomScrollText() {
        val scrollConfig = Serial.LcdScrollConfig(
            isScroll = true,
            interval = CUSTOM_SCROLL_INTERVAL,
            distance = CUSTOM_SCROLL_DISTANCE
        )
        val lcdTextColor = byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0x00)
        val lcdBackgroundColor = byteArrayOf(0x00, 0x00, 0xFF.toByte())
        val command = Serial.getLcdBytes(SCROLL_TEXT, scrollConfig = scrollConfig) +
                Serial.getLcdColorBytes(
                    lcdTextColor, target = Serial.LCD.SCROLL_TEXT_COLOR
                ) +
                Serial.getLcdColorBytes(
                    lcdBackgroundColor, target = Serial.LCD.SCROLL_TEXT_BACKGROUND
                ) +
                Serial.getLcdPersistBytes(true)
        sendLcdCommand(command, "Apply custom scroll text interval=$CUSTOM_SCROLL_INTERVAL,distance=$CUSTOM_SCROLL_DISTANCE")
    }

    private fun sendLcdCommand(command: ByteArray, label: String) {
        val result = robot.sendSerialCommand(Serial.CMD_LCD_TEXT, command)
        val status = if (result == 0) "Success" else "Failed"
        printLog("$label -> $status ($result), data=${command.dataHex}")
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
        private const val SCROLL_TEXT = "Scrolling text demo 139"
        private const val CUSTOM_SCROLL_INTERVAL = 80
        private const val CUSTOM_SCROLL_DISTANCE = 20
    }
}
