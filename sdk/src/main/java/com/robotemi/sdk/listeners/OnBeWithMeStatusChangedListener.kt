package com.robotemi.sdk.listeners

import androidx.annotation.StringDef

interface OnBeWithMeStatusChangedListener {

    /**
     * Listen for status changes during 'beWithMe'.
     *
     *
     * Available statuses:
     *
     *  * [OnBeWithMeStatusChangedListener.ABORT]
     *  * [OnBeWithMeStatusChangedListener.CALCULATING]
     *  * [OnBeWithMeStatusChangedListener.SEARCH]
     *  * [OnBeWithMeStatusChangedListener.START]
     *  * [OnBeWithMeStatusChangedListener.TRACK]
     *  * [OnBeWithMeStatusChangedListener.OBSTACLE_DETECTED]
     *
     *
     * @param status Current status.
     */
    fun onBeWithMeStatusChanged(@BeWithMeStatus status: String)

    @StringDef(ABORT, CALCULATING, SEARCH, START, TRACK, OBSTACLE_DETECTED)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class BeWithMeStatus

    companion object {

        const val ABORT = "abort"

        const val CALCULATING = "calculating"

        const val SEARCH = "search"

        const val START = "start"

        const val TRACK = "track"

        const val OBSTACLE_DETECTED = "obstacle detected"
    }
}
