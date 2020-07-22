package com.robotemi.sdk.listeners

interface OnRobotLiftedListener {
    fun onRobotLifted(isLifted: Boolean, reason: String)
}