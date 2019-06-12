package com.robotemi.sdk.listeners;

public interface OnRobotReadyListener {

    /**
     * Called when connection with robot was established.
     *
     * @param isReady {@code true} when connection is open. {@code false} otherwise.
     */
    void onRobotReady(boolean isReady);
}
