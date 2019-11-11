package com.robotemi.sdk.listeners

interface OnPrivacyModeChangedListener {

    /**
     * Called when privacy mode state changes (true = privacy mode on, false = off).
     *
     */
    fun onPrivacyModeChanged(state: Boolean)
}
