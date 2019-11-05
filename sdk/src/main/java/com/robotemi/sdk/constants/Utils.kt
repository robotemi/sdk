package com.robotemi.sdk.constants

import android.graphics.Bitmap

import java.io.ByteArrayOutputStream

object Utils {
    @JvmStatic
    fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}
