package com.robotemi.sdk

import android.graphics.Bitmap

import java.io.ByteArrayOutputStream

internal object Utils {

    fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}