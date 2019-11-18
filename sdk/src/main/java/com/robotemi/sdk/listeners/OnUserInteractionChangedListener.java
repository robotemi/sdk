package com.robotemi.sdk.listeners;

public interface OnUserInteractionChangedListener {

    /**
     * Listen for user interaction with temi.
     *
     * @param isInteracting true if a user is interacting with temi, otherwise false.
     */
    void onUserInteraction(boolean isInteracting);
}
