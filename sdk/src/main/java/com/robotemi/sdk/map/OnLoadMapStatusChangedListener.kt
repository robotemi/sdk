package com.robotemi.sdk.map

import androidx.annotation.IntDef

interface OnLoadMapStatusChangedListener {

    fun onLoadMapStatusChanged(@Status status: Int)

    @IntDef(
        START,
        COMPLETE,
        ERROR_UNKNOWN,
        ERROR_ABORT_FROM_ROBOX,
        ERROR_ABORT_ON_NOT_CHARGING,
        ERROR_ABORT_BUSY,
        ERROR_ABORT_ON_TIMEOUT,
        ERROR_PB_STREAM_FILE_INVALID,
        ERROR_GET_MAP_DATA
    )
    annotation class Status

    companion object {
        const val COMPLETE = 0
        const val START = 1
        const val ERROR_UNKNOWN = 1000
        const val ERROR_ABORT_FROM_ROBOX = 2000
        const val ERROR_ABORT_ON_NOT_CHARGING = 2001
        const val ERROR_ABORT_BUSY = 2002
        const val ERROR_ABORT_ON_TIMEOUT = 3000
        const val ERROR_PB_STREAM_FILE_INVALID = 4000
        const val ERROR_GET_MAP_DATA = 5000
    }
}