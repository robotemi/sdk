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

    /**
     * Generate LCD text command bytes for both normal text and scrolling text components.
     * @param target kept for source compatibility. The LCD text target is fixed internally for firmware compatibility.
     */
    @JvmOverloads
    fun getLcdBytes(
        text: String,
        @Suppress("UNUSED_PARAMETER") target: String = LCD.TEXT_0_TEXT,
        scrollConfig: LcdScrollConfig = LcdScrollConfig(),
        charset: Charset = Charsets.UTF_8
    ): ByteArray {
        val textBytes = getLcdTextBytes(text, LCD.TEXT_0_TEXT, charset) +
                getLcdTextBytes(text, LCD.SCROLL_TEXT, charset)
        return if (scrollConfig.isScroll) {
            textBytes +
                getLcdVisibleBytes(LCD.SCROLL_TEXT_COMPONENT, true, charset) +
                getLcdVisibleBytes(LCD.TEXT_0, true, charset) +
                getLcdNumberPropertyBytes(LCD.SCROLL_TEXT_DIRECTION, scrollConfig.direction, charset) +
                getLcdScrollSpeedBytes(scrollConfig.interval, scrollConfig.distance, charset) +
                getLcdNumberPropertyBytes(LCD.SCROLL_TEXT_ENABLE, 1, charset)
        } else {
            textBytes +
                getLcdVisibleBytes(LCD.TEXT_0, true, charset) +
                getLcdVisibleBytes(LCD.SCROLL_TEXT_COMPONENT, false, charset) +
                getLcdNumberPropertyBytes(LCD.SCROLL_TEXT_ENABLE, 0, charset)
        }
    }

    fun getLcdBytes(text: String, target: String, charset: Charset): ByteArray {
        return getLcdBytes(text, target, LcdScrollConfig(), charset)
    }

    fun getLcdColorBytes(textColor: ByteArray, target: String = LCD.TEXT_0_COLOR, charset: Charset = Charsets.UTF_8): ByteArray {
        return getLcdNumberPropertyBytes(target, RGB565.convertRgb888To565(textColor), charset)
    }

    fun getLcdPersistBytes(persist: Boolean, target: String = LCD.TEXT_0_PERSIST, charset: Charset = Charsets.UTF_8): ByteArray {
        return getLcdCommandBytes("$target=\"$persist\"", charset)
    }

    private fun getLcdScrollSpeedBytes(
        @IntRange(from = 80) interval: Int = LCD.SCROLL_INTERVAL_DEFAULT,
        @IntRange(from = 2, to = 50) distance: Int = LCD.SCROLL_DISTANCE_DEFAULT,
        charset: Charset = Charsets.UTF_8
    ): ByteArray {
        val safeInterval = interval.coerceAtLeast(LCD.SCROLL_INTERVAL_MIN)
        val safeDistance = distance.coerceIn(LCD.SCROLL_DISTANCE_MIN, LCD.SCROLL_DISTANCE_MAX)
        return getLcdNumberPropertyBytes(LCD.SCROLL_TEXT_INTERVAL, safeInterval, charset) +
                getLcdNumberPropertyBytes(LCD.SCROLL_TEXT_DISTANCE, safeDistance, charset)
    }

    private fun getLcdNumberPropertyBytes(target: String, value: Int, charset: Charset = Charsets.UTF_8): ByteArray {
        return getLcdCommandBytes("$target=$value", charset)
    }

    private fun getLcdTextBytes(text: String, target: String, charset: Charset = Charsets.UTF_8): ByteArray {
        return getLcdCommandBytes("$target=\"$text\"", charset)
    }

    private fun getLcdVisibleBytes(target: String, visible: Boolean, charset: Charset = Charsets.UTF_8): ByteArray {
        return getLcdCommandBytes("vis $target,${if (visible) 1 else 0}", charset)
    }

    private fun getLcdCommandBytes(command: String, charset: Charset = Charsets.UTF_8): ByteArray {
        return command.toByteArray(charset).plus(LCD_COMMAND_END)
    }

    object LCD {
        const val TEXT_0 = "t0"
        const val TEXT_0_TEXT = "t0.txt"
        const val TEXT_0_COLOR = "t0.pco"
        const val TEXT_0_BACKGROUND = "t0.bco"
        const val TEXT_0_PERSIST = "t0.persist"
        const val SCROLL_TEXT_COMPONENT = "g0"
        const val SCROLL_TEXT = "g0.txt"
        const val SCROLL_TEXT_DIRECTION = "g0.dir"
        const val SCROLL_TEXT_INTERVAL = "g0.tim"
        const val SCROLL_TEXT_DISTANCE = "g0.dis"
        const val SCROLL_TEXT_ENABLE = "g0.en"
        const val SCROLL_DIRECTION_LEFT_TO_RIGHT = 0
        const val SCROLL_DIRECTION_RIGHT_TO_LEFT = 1
        const val SCROLL_DIRECTION_TOP_TO_BOTTOM = 2
        const val SCROLL_DIRECTION_BOTTOM_TO_TOP = 3
        const val SCROLL_INTERVAL_DEFAULT = 80
        const val SCROLL_INTERVAL_MIN = 80
        const val SCROLL_DISTANCE_DEFAULT = 8
        const val SCROLL_DISTANCE_MIN = 2
        const val SCROLL_DISTANCE_MAX = 50

    }

    data class LcdScrollConfig @JvmOverloads constructor(
        val isScroll: Boolean = true,
        @IntRange(from = 0, to = 3) val direction: Int = LCD.SCROLL_DIRECTION_RIGHT_TO_LEFT,
        @IntRange(from = 80) val interval: Int = LCD.SCROLL_INTERVAL_DEFAULT,
        @IntRange(from = 2, to = 50) val distance: Int = LCD.SCROLL_DISTANCE_DEFAULT
    )

    private val LCD_COMMAND_END = byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte())

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