package com.robotemi.sdk.navigation.listener

import androidx.annotation.IntDef

interface OnPositingStatusChangedListener {
    fun onPositingStatusChanged(@Status status: Int)

    @IntDef(START, GOING, COMPLETE, ABORT)
    annotation class Status

    companion object {
        const val START = 0
        const val GOING = 1
        const val COMPLETE = 2
        const val ABORT = 3
    }
}