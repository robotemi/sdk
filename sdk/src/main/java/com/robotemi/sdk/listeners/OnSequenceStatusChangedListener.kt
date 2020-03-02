package com.robotemi.sdk.listeners

import androidx.annotation.StringDef

interface OnSequenceStatusChangedListener {

    fun onSequenceStatusChanged(@SequenceStatus status: String)

    @StringDef(START, COMPLETE, ERROR)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class SequenceStatus

    companion object {

        const val START = "start"

        const val COMPLETE = "complete"

        const val ERROR = "error"
    }
}