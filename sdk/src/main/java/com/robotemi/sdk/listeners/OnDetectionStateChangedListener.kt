package com.robotemi.sdk.listeners

interface OnDetectionStateChangedListener {

    /**
     * Listen for user detection.
     *
     * @param isDetected True if user was detected, false if user detected lost.
     */
    fun onDetectionStateChanged(isDetected: Boolean)
}