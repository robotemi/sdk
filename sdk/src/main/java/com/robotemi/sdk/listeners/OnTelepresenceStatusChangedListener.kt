package com.robotemi.sdk.listeners

import com.robotemi.sdk.telepresence.CallState

/**
 * @param sessionId is no longer available from startTelepresence return value, it is not required any more.
 */
abstract class OnTelepresenceStatusChangedListener(var sessionId: String) {

    /**
     * Called when Telepresence status was changed.
     *
     */
    abstract fun onTelepresenceStatusChanged(callState: CallState)
}
