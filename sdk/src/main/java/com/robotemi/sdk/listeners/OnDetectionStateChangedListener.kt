package com.robotemi.sdk.listeners

import androidx.annotation.IntDef

interface OnDetectionStateChangedListener {

    /**
     * Available status:
     *  * [OnDetectionStateChangedListener.IDLE]
     *  * [OnDetectionStateChangedListener.LOST]
     *  * [OnDetectionStateChangedListener.DETECTED]
     *
     *
     * @param state Current state.
     */
    fun onDetectionStateChanged(@DetectionStatus state: Int)

    @IntDef(IDLE, LOST, DETECTED)
    @Retention(AnnotationRetention.SOURCE)
    annotation class DetectionStatus {
        companion object {
            fun fromValue(@DetectionStatus state: Int): String {
                return when (state) {
                    IDLE -> "Idle"
                    LOST -> "Lost"
                    DETECTED -> "Detected"
                    else -> ""
                }
            }
        }
    }
    companion object {

        const val IDLE = 0

        const val LOST = 1

        const val DETECTED = 2
    }
}