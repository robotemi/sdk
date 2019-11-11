package com.robotemi.sdk.listeners

import androidx.annotation.StringDef

interface OnWelcomingModeStatusChangedListener {

    /**
     * Listen for status changes during 'Welcoming mode'.
     *
     *
     * Available status:
     *
     *  * [OnWelcomingModeStatusChangedListener.ACTIVE]
     *  * [OnWelcomingModeStatusChangedListener.IDLE]
     *
     *
     * @param status Current status.
     */
    fun onWelcomingModeStatusChanged(@WelcomingModeStatus status: String)

    @StringDef(ACTIVE, IDLE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class WelcomingModeStatus

    companion object {

        const val ACTIVE = "active"

        const val IDLE = "idle"
    }
}
