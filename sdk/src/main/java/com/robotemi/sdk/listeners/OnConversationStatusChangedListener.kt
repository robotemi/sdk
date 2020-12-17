package com.robotemi.sdk.listeners

import androidx.annotation.IntDef

interface OnConversationStatusChangedListener {
    fun onConversationStatusChanged(@Status status: Int, text: String)

    @IntDef(IDLE, LISTENING, THINKING, SPEAKING)
    annotation class Status

    companion object {
        const val IDLE = 0
        const val LISTENING = 1
        const val THINKING = 2
        const val SPEAKING = 3
    }
}