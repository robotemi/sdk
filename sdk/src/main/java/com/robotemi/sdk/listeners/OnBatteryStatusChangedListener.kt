package com.robotemi.sdk.listeners

import com.robotemi.sdk.BatteryData

interface OnBatteryStatusChangedListener {

    /**
     * Called when there's an update to temi's battery data
     *
     */
    fun onBatteryStatusChanged(batteryData: BatteryData?)
}
