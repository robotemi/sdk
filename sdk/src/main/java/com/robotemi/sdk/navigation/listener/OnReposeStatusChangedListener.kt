package com.robotemi.sdk.navigation.listener

import androidx.annotation.IntDef

interface OnReposeStatusChangedListener {

    fun onReposeStatusChanged(@Status status: Int, description: String)

    @IntDef(
        IDLE,
        REPOSE_REQUIRED,
        REPOSING_START,
        REPOSING_GOING,
        REPOSING_COMPLETE,
        REPOSING_OBSTACLE_DETECTED,
        REPOSING_ABORT
    )
    annotation class Status

    companion object {
        const val IDLE = 0
        const val REPOSE_REQUIRED = 1
        const val REPOSING_START = 2
        const val REPOSING_GOING = 3
        const val REPOSING_COMPLETE = 4
        const val REPOSING_OBSTACLE_DETECTED = 5
        const val REPOSING_ABORT = 6
    }
}