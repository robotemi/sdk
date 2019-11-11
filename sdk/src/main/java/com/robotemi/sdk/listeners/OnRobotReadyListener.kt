package com.robotemi.sdk.listeners

interface OnRobotReadyListener {

    /**
     * Called when connection with robot was established.
     *
     * @param isReady `true` when connection is open. `false` otherwise.
     */
    fun onRobotReady(isReady: Boolean)
}
