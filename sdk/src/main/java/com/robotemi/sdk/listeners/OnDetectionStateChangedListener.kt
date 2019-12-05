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
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class DetectionStatus
    companion object {

        const val IDLE = 0

        const val LOST = 1

        const val DETECTED = 2
    }
}