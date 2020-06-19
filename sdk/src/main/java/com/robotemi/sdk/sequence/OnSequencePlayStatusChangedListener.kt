package com.robotemi.sdk.sequence

import androidx.annotation.IntDef

interface OnSequencePlayStatusChangedListener {

    fun onSequencePlayStatusChanged(sequenceId: String, @Status status: Int)

    @IntDef(START, COMPLETE, ERROR)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class Status

    companion object {
        const val START = 1
        const val COMPLETE = 2
        const val ERROR = 0
    }
}