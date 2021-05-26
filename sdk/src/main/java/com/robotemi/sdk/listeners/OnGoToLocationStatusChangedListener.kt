package com.robotemi.sdk.listeners

import androidx.annotation.StringDef

interface OnGoToLocationStatusChangedListener {

    /**
     * Listen for status changes during 'go to location'.
     *
     *
     * Available statuses:
     *
     *  * [OnGoToLocationStatusChangedListener.START]
     *  * [OnGoToLocationStatusChangedListener.CALCULATING]
     *  * [OnGoToLocationStatusChangedListener.GOING]
     *  * [OnGoToLocationStatusChangedListener.COMPLETE]
     *  * [OnGoToLocationStatusChangedListener.ABORT]
     *  * [OnGoToLocationStatusChangedListener.REPOSING]
     *
     *
     * @param location Location of GoTo response.
     * @param status   Current status.
     */
    fun onGoToLocationStatusChanged(
        location: String,
        @GoToLocationStatus status: String,
        descriptionId: Int,
        description: String
    )

    @StringDef(START, CALCULATING, GOING, COMPLETE, ABORT, REPOSING)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class GoToLocationStatus

    companion object {

        const val START = "start"

        const val CALCULATING = "calculating"

        const val GOING = "going"

        const val COMPLETE = "complete"

        const val ABORT = "abort"

        const val REPOSING = "reposing"
    }
}
