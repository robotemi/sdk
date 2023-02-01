package com.robotemi.sdk.serial

import androidx.annotation.IntRange
import java.nio.charset.Charset

@Suppress("MemberVisibilityCanBePrivate")
object Serial {

    // Door
    const val CMD_DOOR_CALIBRATE = 0x01 // calibrate door, data is empty
    const val CMD_DOOR_OPEN = 0x02 // open door, data is 01, 02, 03 for each door
    const val CMD_DOOR_CLOSE = 0x03 // close door, data is 01, 02, 03 for each door
    const val CMD_DOOR_MOTOR = 0x08 // turn on/off door motor. 01 is OFF, 00 is ON, e.g. OFF 5A 01 08 00 01 00 01 9B, ON 5A 01 08 00 01 00 00 9C

    // Light Strip, Use

    /**
     * control light strip with breathing RGB, 01 (RGB mode 01 always on, 02 breath, 03 marquee.), R, G, B (Start color), R, G, B (End color), 00/01 (direction), 00, 00, 00, 00 (interval in ms), checksum.
     * Use [getStripBytes] to generate data frame
     */
    const val CMD_STRIP_LIGHT = 0x04

    // Tray
    const val CMD_TRAY_CALIBRATE = 0x1007 // calibrate tray weight sensor, data is empty.
    const val CMD_TRAY_SENSOR = 0x1008 // e.g. 5A 01 08 10 02 00 01 (<-- trayNum 2) 8B. -> Query Tray 2 has things on it or not
    const val CMD_TRAY_LIGHT = 0x1009 // control tray light. data frame is tray number + R + G + B

    /**
     * control LCD screen text on the back.
     * Use [getLcdBytes] to generate data frame
     */
    const val CMD_LCD_TEXT = 0x100B

    const val RESP_TRAY_SENSOR = 0x1008 // e.g. 5A 01 08 10 02 00 00 (<-- trayNum 1) 01 (<-- 1: T, 0: F) 8A. -> Tray 1 has things on it or not
    const val RESP_TRAY_BACK_BUTTON = 0x06 // 5A 01 06 00 01 00 0/1/2 checksum

    // System
    const val CMD_SYSTEM_GET_VERSION = 0x11 // get MCU version, 5A 01 11 00 00 00 94
    const val CMD_SYSTEM_START_OTA = 0x1005 // hidden API, not callable form SDK, start DFU OTA mode, 5A 01 05 10 00 00 90
    const val RESP_SYSTEM_VERSION = 0x1003 // 5A, 01, 03, 10, 26, 00, 31, 2E, 34, 2E, 35, 20, 30, 38, 30, 38, 32, 30, 32, 32, 20, 7C, 20, 62, 6F, 6F, 74, 6C, 6F, 61, 64, 65, 72, 20, 76, 65, 72, 73, 69, 6F, 6E, 3A, 20, 00, 59


    val ByteArray.cmd: Int
        get() = (this[3].toInt() shl 8) + this[2]

    val ByteArray.weight: Int
        get() {
            if (this.size <= 2) {
                return 0
            }
            return (this[5].toInt() shl 24) or (this[4].toUByte()
                .toInt() shl 16) or (this[3].toUByte()
                .toInt() shl 8) or this[2].toUByte().toInt()
        }

    val ByteArray.dataFrame: ByteArray
        get() {
            val dataLength = (this[5].toInt() shl 8) + this[4]
            return copyOfRange(6, 6 + dataLength)
        }

    val ByteArray.dataHex: List<String>
        get() = map { String.format("%02X", it) }

    fun getLcdBytes(text: String, target: String = "t0.txt", charset: Charset = Charsets.UTF_8): ByteArray {
        val content = "$target=\"$text\""
        return content.toByteArray(charset).plus(byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()))
    }

    fun getLcdColorBytes(textColor: ByteArray, target: String = LCD.TEXT_0_COLOR, charset: Charset = Charsets.UTF_8): ByteArray {
        val content = "$target=${RGB565.convertRgb888To565(textColor)}"
        return content.toByteArray(charset).plus(byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()))
    }

    object LCD {
        const val TEXT_0_COLOR = "t0.pco"
        const val TEXT_0_BACKGROUND = "t0.bco"
    }

    /**
     * Get Strip LED data frame
     * @param mode 1 always on;
     *             2 breathing;
     *             3 marquee,
     * @param primaryColor color for always on, or primary color breathing and marquee.
     * @param secondaryColor color for breathing out, or marque background
     * @param direction used by marque, 0 move to left, 1 move to right, 2 bouncing
     * @param interval milliseconds for every change of color in breath and marquee.
     *
     *
     */
    fun getStripBytes(@IntRange(from = 1, to = 3)
                      mode: Int, // 01 always on, 02 breath, 03 marquee
                      primaryColor: ByteArray, // R, G, B
                      secondaryColor: ByteArray =  byteArrayOf(0x00, 0x00, 0x00), // R, G, B
                      @IntRange(from = 0, to = 1)
                      direction: Int = 0,
                      @IntRange(from = 0, to = 1000)
                      interval: Int = 0 // millisecond, range from 0 to 1000
    ): ByteArray {
        if (primaryColor.size != 3 || secondaryColor.size != 3) {
            throw IllegalArgumentException("Color should be in RGB")
        }
        return byteArrayOf(
            mode.toByte(),
            primaryColor[0], primaryColor[1], primaryColor[2],
            secondaryColor[0], secondaryColor[1], secondaryColor[2],
            direction.toByte(),
            interval.rem(0xFF).toByte(), interval.div(0xFF).toByte(), 0x00, 0x00
        )
    }
}

/**
 * https://trolsoft.ru/en/articles/rgb565-color-picker
 */
internal object RGB565 {
    fun convertRgb888To565(byteArray: ByteArray): Int {
        if (byteArray.size != 3) return 0
        val r = (byteArray[0].toUByte().toInt() and 0xf8) shl 8
        val g = (byteArray[1].toUByte().toInt() and 0xfc) shl 3
        val b = (byteArray[2].toUByte().toInt() and 0xf8) shr 3
        return r or g or b
    }
}