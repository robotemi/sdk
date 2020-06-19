package com.robotemi.sdk.listeners;

interface OnUserInteractionChangedListener {

    /**
     * Listen for user interaction with temi.
     *
     * @param isInteracting true if a user is interacting with temi, otherwise false.
     */
    fun onUserInteraction(isInteracting: Boolean)
}
