package com.robotemi.sdk.listeners

import com.robotemi.sdk.map.Layer

interface OnZoneEntranceStatusChangedListener {

    /**
     *
     * Called when the area where the robot is located changes
     */
    fun onZoneEntranceStatusChanged(layers: List<Layer>)
}