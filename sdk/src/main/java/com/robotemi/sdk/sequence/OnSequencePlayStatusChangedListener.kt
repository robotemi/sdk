package com.robotemi.sdk.sequence

import androidx.annotation.IntDef

interface OnSequencePlayStatusChangedListener {

    fun onSequencePlayStatusChanged(@Status status: Int, sequenceId: String?)

    @IntDef(IDLE, PREPARING, PLAYING, ERROR)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Status

    companion object {
        const val IDLE = 0
        const val PREPARING = 1
        const val PLAYING = 2
        const val ERROR = -1
    }
}