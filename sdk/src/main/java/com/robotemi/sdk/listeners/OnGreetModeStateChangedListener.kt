package com.robotemi.sdk.listeners

import androidx.annotation.IntDef

interface OnGreetModeStateChangedListener {

    fun onGreetModeStateChanged(@State state: Int)

    @IntDef(HOLD, ERROR, PREPARING, SEARCHING, GREETING, INTERACTION, POST_INTERACTION)
    annotation class State {
        companion object {

            fun fromValue(@State state: Int): String {
                return when (state) {
                    HOLD -> "Hold"
                    ERROR -> "Error"
                    SEARCHING -> "Searching"
                    PREPARING -> "Preparing"
                    GREETING -> "Greeting"
                    INTERACTION -> "Interaction"
                    POST_INTERACTION -> "PostInteraction"
                    else -> ""
                }
            }
        }
    }

    companion object {
        const val HOLD = 0
        const val ERROR = -1
        const val SEARCHING = 1
        const val PREPARING = 2
        const val GREETING = 3
        const val INTERACTION = 4
        const val POST_INTERACTION = 5
    }
}