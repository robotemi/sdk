package com.robotemi.sdk.listeners

import com.robotemi.sdk.model.CallEventModel

interface OnTelepresenceEventChangedListener {

    /**
     * Available event:
     *
     *  @param callEventModel Call info.
     */
    fun onTelepresenceEventChanged(callEventModel: CallEventModel)
}