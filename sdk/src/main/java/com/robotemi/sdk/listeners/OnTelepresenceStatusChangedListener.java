package com.robotemi.sdk.listeners;

import com.robotemi.sdk.telepresence.CallState;

public abstract class OnTelepresenceStatusChangedListener {

    public String sessionId;

    public OnTelepresenceStatusChangedListener(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Called when Telepresence status was changed.
     *
     */
    public abstract void onTelepresenceStatusChanged(CallState callState);
}
