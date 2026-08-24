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
        binding.ibBack.setOnClickListener { finish() }
        binding.btnApplyScrollText.setOnClickListener { applyScrollingText() }
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

    private fun applyScrollingText() {
        val text = binding.etScrollText.text.toString().ifBlank { SAMPLE_TEXT }
        val payload = Serial.getLcdBytes(text, target = "$SCROLL_TARGET.txt") +
                lcdNumberCommand("$SCROLL_TARGET.dir", direction) +
                lcdNumberCommand("$SCROLL_TARGET.en", 1)
        sendSingleCommand(payload, "Apply $SCROLL_TARGET scroll text")
    }

    private fun setScrollingEnabled(enabled: Boolean) {
        sendSingleCommand(
            lcdNumberCommand("$SCROLL_TARGET.en", if (enabled) 1 else 0),
            "$SCROLL_TARGET ${if (enabled) "start" else "stop"}"
        )
    }

    private fun setScrollDirection() {
        sendSingleCommand(
            lcdNumberCommand("$SCROLL_TARGET.dir", direction),
            "$SCROLL_TARGET.dir=${directionLabel()}"
        )
    }

    private fun sendSingleCommand(command: ByteArray, label: String) {
        val result = robot.sendSerialCommand(Serial.CMD_LCD_TEXT, command)
        val status = if (result == 0) "Success" else "Failed"
        printLog("$label -> $status ($result), data=${command.dataHex}")
    }

    private fun lcdNumberCommand(target: String, value: Int): ByteArray {
        return lcdCommand("$target=$value")
    }

    private fun lcdCommand(command: String): ByteArray {
        return command.toByteArray().plus(LCD_COMMAND_END)
    }

    private fun directionLabel(): String {
        return when (direction) {
            LEFT_TO_RIGHT -> "left_to_right"
            TOP_TO_BOTTOM -> "top_to_bottom"
            BOTTOM_TO_TOP -> "bottom_to_top"
            else -> "right_to_left"
        }
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
        private const val SCROLL_TARGET = "t0"
        private const val LEFT_TO_RIGHT = 0
        private const val RIGHT_TO_LEFT = 1
        private const val TOP_TO_BOTTOM = 2
        private const val BOTTOM_TO_TOP = 3
        private const val SAMPLE_TEXT = "newtxt139"
        private val LCD_COMMAND_END = byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte())
    }
}
