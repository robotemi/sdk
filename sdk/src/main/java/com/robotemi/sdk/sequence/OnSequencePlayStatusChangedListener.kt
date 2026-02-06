package com.robotemi.sdk.sequence

import androidx.annotation.IntDef

interface OnSequencePlayStatusChangedListener {

    /**
     * Callback when the playback status of the sequence changes.
     *
     * @param status Playback status.
     *
     * @param sequenceId The sequence id that has undergone a state change.
     *
     */
    fun onSequencePlayStatusChanged(@Status status: Int, sequenceId: String?)


    /**
     * Callback when the current execution step of the sequence changes.
     *
     * @param sequenceId The id of the sequence being executed.
     *
     * @param stepIndex The index of the currently executing step (starting from 1).
     *
     * @param totalSteps The total number of steps in this sequence.
     *
     */
    fun onSequenceStepChanged(sequenceId: String, stepIndex: Int, totalSteps: Int) {}

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