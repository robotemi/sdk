package com.robotemi.sdk.map

import androidx.annotation.IntDef

interface OnLoadFloorStatusChangedListener {

    fun onLoadFloorStatusChanged(@Status status: Int)

    @IntDef(
        START,
        COMPLETE,
        ERROR
    )
    annotation class Status

    companion object {
        const val COMPLETE = 0
        const val START = 1
        const val ERROR = -1
    }
}