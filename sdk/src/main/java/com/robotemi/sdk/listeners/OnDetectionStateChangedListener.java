package com.robotemi.sdk.listeners;

public interface OnDetectionStateChangedListener {

    /**
     * Listen for Constraint Follow state.
     *
     * @param isDetected true if temi has detected an object, otherwise false.
     */
    void onDetectionStateChanged(boolean isDetected);
}
