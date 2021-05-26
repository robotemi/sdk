package com.robotemi.sdk.listeners

import androidx.annotation.StringDef

interface OnMovementStatusChangedListener {
    fun onMovementStatusChanged(@Type type: String, @Status status: String)

    @StringDef(TYPE_SKID_JOY, TYPE_TURN_BY)
    annotation class Type

    @StringDef(
        STATUS_START,
        STATUS_GOING,
        STATUS_OBSTACLE_DETECTED,
        STATUS_NODE_INACTIVE,
        STATUS_CALCULATING,
        STATUS_COMPLETE,
        STATUS_ABORT
    )
    annotation class Status

    companion object {
        const val TYPE_SKID_JOY = "skidJoy"
        const val TYPE_TURN_BY = "turnBy"

        const val STATUS_START = "start"
        const val STATUS_GOING = "going"
        const val STATUS_OBSTACLE_DETECTED = "obstacle detected"
        const val STATUS_NODE_INACTIVE = "node inactive"
        const val STATUS_CALCULATING = "calculating"
        const val STATUS_COMPLETE = "complete"
        const val STATUS_ABORT = "abort"
    }
}