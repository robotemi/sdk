package com.robotemi.sdk.voice

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WakeupRequest(
    val wakeupResponse: Boolean = false
): Parcelable

/**
 * Where the wake up is triggered from.
 *
 * [ROBOX] - Wake up is triggered by voice, the wake word detection is running on Robox.
 * [ANDROID] - Wake up is triggered by voice, the wake word detection is running on Android.
 * [TOP_BAR] - Wake up is triggered by user clicking on the top bar wake up button.
 * [SDK] - Wake up is triggered by SDK.
 * [ANALOG] - Wake up is triggered by code in temi launcher in flows like continuous listening.
 * [UNKNOWN] - Wake up is triggered by unknown source.
 */
enum class WakeupOrigin {
    ROBOX,
    ANDROID,
    TOP_BAR,
    SDK,
    ANALOG,
    UNKNOWN,
    ;

    companion object {
        fun parse(origin: String?): WakeupOrigin {
            return when (origin) {
                "ROBOX" -> ROBOX
                "ANDROID" -> ANDROID
                "ANALOG" -> ANALOG
                "TOPBAR" -> TOP_BAR
                "SDK" -> SDK
                else -> UNKNOWN
            }

        }
    }
}
