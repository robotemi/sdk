package com.robotemi.sdk.listeners

import com.robotemi.sdk.constants.HardButton


interface OnButtonStatusChangedListener {

    fun onButtonStatusChanged(hardButton: HardButton, status: HardButton.Status)

}