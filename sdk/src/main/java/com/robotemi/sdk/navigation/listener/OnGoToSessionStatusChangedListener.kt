package com.robotemi.sdk.navigation.listener

import androidx.annotation.IntDef

interface OnGoToSessionStatusChangedListener {
    fun onGoToSessionStatusChanged(@Status status: Int)

    @IntDef(
        IDLE,
        FIRST_GO_TO,
        REPOSE,
        SECOND_GO_TO,
        COMPLETED,
        FAILED
    )
    annotation class Status

    companion object {
        const val IDLE = 0
        const val FIRST_GO_TO = 1
        const val REPOSE = 2
        const val SECOND_GO_TO = 3
        const val COMPLETED = 4
        const val FAILED = 5
    }
}