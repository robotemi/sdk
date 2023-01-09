package com.robotemi.sdk.serial

import org.junit.Test

internal class RGB565Test {

    @Test
    fun getRGB565DecimalFromColors() {
        val black = RGB565.convertRgb888To565(byteArrayOf(0x00, 0x00, 0x00))
        println(black)
        assert(black == 0)

        val white =
            RGB565.convertRgb888To565(byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()))
        println(white)
        assert(white == 65535)

        val yellow = RGB565.convertRgb888To565(byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0x00))
        println(yellow)
        assert(yellow == 65504)

        val cyan = RGB565.convertRgb888To565(byteArrayOf(0x00, 0xFF.toByte(), 0xFF.toByte()))
        println(cyan)
        assert(cyan == 2047)

        val brown = RGB565.convertRgb888To565(byteArrayOf(0xBD.toByte(), 0x8A.toByte(), 0x00))
        println(brown)
        assert(brown == 48192)
    }
}