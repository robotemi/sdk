package com.robotemi.sdk.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest
import com.robotemi.sdk.listeners.OnSerialRawDataListener
import com.robotemi.sdk.serial.Serial
import com.robotemi.sdk.serial.Serial.cmd
import com.robotemi.sdk.serial.Serial.dataFrame
import com.robotemi.sdk.serial.Serial.dataHex
import com.robotemi.sdk.serial.Serial.getLcdBytes
import com.robotemi.sdk.serial.Serial.getLcdColorBytes
import kotlinx.android.synthetic.main.activity_serial.*

class SerialActivity : AppCompatActivity(), OnSerialRawDataListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serial)

        Robot.getInstance().addOnSerialRawDataListener(this)
    }

    override fun onResume() {
        ibBack.setOnClickListener { finish() }
        btnVersion.setOnClickListener {
            Robot.getInstance().sendSerialCommand(Serial.CMD_SYSTEM_GET_VERSION, byteArrayOf())
        }
        btnTrayRGB.setOnClickListener {
            Robot.getInstance().sendSerialCommand(
                Serial.CMD_TRAY_LIGHT,
                byteArrayOf(0x00, 0xff.toByte(), 0x00, 0x00)
            ) // TRAY 1 RED
            Robot.getInstance().sendSerialCommand(
                Serial.CMD_TRAY_LIGHT,
                byteArrayOf(0x01, 0x00, 0xff.toByte(), 0x00)
            ) // TRAY 2 GREEN
            Robot.getInstance().sendSerialCommand(
                Serial.CMD_TRAY_LIGHT,
                byteArrayOf(0x02, 0x00, 0x00, 0xff.toByte())
            ) // TRAY 3 BLUE
        }

        btnStripConstant.setOnClickListener {
            val color = if (it.tag == true) {
                it.tag = false
                byteArrayOf(0xff.toByte(), 0x00, 0x00)  // Strip always on red
            } else {
                it.tag = true
                byteArrayOf(0x00, 0xff.toByte(), 0x00) // Strip always on green
            }
            Robot.getInstance().sendSerialCommand(
                Serial.CMD_STRIP_LIGHT,
                Serial.getStripBytes(mode = 1, primaryColor = color)
            ) // Strip constant color
        }

        btnStripOFF.setOnClickListener {
            Robot.getInstance().sendSerialCommand(
                Serial.CMD_STRIP_LIGHT,
                Serial.getStripBytes(mode = 1, primaryColor = byteArrayOf(0x00, 0x00, 0x00))
            ) // Strip always on Black, meaning turn off LED strip.
        }
        btnStripBreathing.setOnClickListener {
            val (primaryColor, secondaryColor) = if (it.tag == true) {
                it.tag = false
                byteArrayOf(0xff.toByte(), 0x00, 0x00) to byteArrayOf(0x00, 0xff.toByte(), 0x00)  // Strip breathing red to green
            } else {
                it.tag = true
                byteArrayOf(0x00, 0x00, 0xff.toByte()) to byteArrayOf(0xff.toByte(), 0xff.toByte(), 0x00) // Strip breathing blue to yellow
            }
            Robot.getInstance().sendSerialCommand(
                Serial.CMD_STRIP_LIGHT,
                Serial.getStripBytes(
                    mode = 2,
                    primaryColor = primaryColor,
                    secondaryColor = secondaryColor,
                    interval = 20
                )
            ) // Strip breathing
        }
        btnStripRunning.setOnClickListener {
            val (primaryColor, secondaryColor) = if (it.tag == true) {
                it.tag = false
                byteArrayOf(0xff.toByte(), 0x00, 0x00) to byteArrayOf(0x00, 0x22, 0x22)  // Strip running red
            } else {
                it.tag = true
                byteArrayOf(0x20.toByte(), 0xD2.toByte(), 0x9A.toByte()) to byteArrayOf(0x00, 0x22, 0x22) // Strip running green
            }
            Robot.getInstance().sendSerialCommand(
                Serial.CMD_STRIP_LIGHT,
                // 5A 01 04 00 0C 00 03 20 D2 9A 00 00 FF 00 14 00 00 00 F3
                Serial.getStripBytes(
                    mode = 3,
                    primaryColor = primaryColor,
                    secondaryColor = secondaryColor,
                    direction = 1,
                    interval = 20
                )
            ) // Strip running
        }

        btnTrayQuery.setOnClickListener {
            Robot.getInstance().sendSerialCommand(
                Serial.CMD_TRAY_SENSOR,
                byteArrayOf(0x00)
            )
        }

        btnLcdTextTime.setOnClickListener {
            Robot.getInstance().sendSerialCommand(
                Serial.CMD_LCD_TEXT,
                getLcdBytes(System.currentTimeMillis().toString().substring(5))
            )
        }

        btnLcdTextColor.setOnClickListener {
            val color = if (it.tag == true) {
                it.tag = false
                byteArrayOf(0xFF.toByte(), 0x00, 0x00)
            } else {
                it.tag = true
                byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0x00)
            }
            Robot.getInstance().sendSerialCommand(
                Serial.CMD_LCD_TEXT,
                getLcdColorBytes(color, target = Serial.LCD.TEXT_0_COLOR)
            )
        }

        btnLcdBackgroundColor.setOnClickListener {
            val color = if (it.tag == true) {
                it.tag = false
                byteArrayOf(0x00, 0xFF.toByte(), 0x00)
            } else {
                it.tag = true
                byteArrayOf(0x00, 0xFF.toByte(), 0xFF.toByte())
            }
            Robot.getInstance().sendSerialCommand(
                Serial.CMD_LCD_TEXT,
                getLcdColorBytes(color, target = Serial.LCD.TEXT_0_BACKGROUND)
            )
        }
        super.onResume()
    }

    override fun onDestroy() {
        Robot.getInstance().removeOnSerialRawDataListener(this)
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    override fun onSerialRawData(data: ByteArray) {
        // Command id of response
        val cmd = data.cmd

        // Data frame of response
        val dataFrame = data.dataFrame

        // To see the hex array of raw data
        Log.d("Serial", "cmd $cmd raw data ${data.dataHex}")
        tvResp.text = data.dataHex.toString()
        when (cmd) {
            Serial.RESP_TRAY_SENSOR -> {
                // The first place in data frame stands for tray number, starts from 0
                val trayNum = dataFrame[0].toInt() + 1
                val loaded = dataFrame[1].toInt() == 1
                val speech = if (loaded) {
                    "Tray $trayNum is loaded"
                } else {
                    "Tray $trayNum is empty"
                }
                Robot.getInstance().speak(
                    TtsRequest.create(
                        speech,
                        isShowOnConversationLayer = false,
                        cached = true
                    )
                )
                if (loaded) {
                    Robot.getInstance().sendSerialCommand(
                        Serial.CMD_TRAY_LIGHT,
                        byteArrayOf(data[6], 0xFF.toByte(), 0x00, 0x00)
                    )
                } else {
                    Robot.getInstance().sendSerialCommand(
                        Serial.CMD_TRAY_LIGHT,
                        byteArrayOf(data[6], 0x20, 0xD1.toByte(), 0x99.toByte())
                    )
                }
            }
            Serial.RESP_TRAY_BACK_BUTTON -> {
                val event = dataFrame.firstOrNull() ?: return
                Log.d("Serial", "Button data frame $event")
                val speech = when (event.toInt()) {
                    0 -> "touch"
                    1 -> "press"
                    2 -> "" // release after press
                    else -> ""
                }
                Robot.getInstance().speak(
                    TtsRequest.create(
                        speech,
                        isShowOnConversationLayer = false,
                        cached = true
                    )
                )
            }
            Serial.RESP_SYSTEM_VERSION -> {
                val decode = dataFrame.decodeToString()
                Log.d("Serial", "decode $decode")
                btnVersion.text = "Version:${dataFrame.decodeToString()}"
            }
        }
    }
}