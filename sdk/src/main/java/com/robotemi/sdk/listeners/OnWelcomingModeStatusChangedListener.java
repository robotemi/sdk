package com.robotemi.sdk.listeners;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface OnWelcomingModeStatusChangedListener {

    String ACTIVE = "active";

    String IDLE = "idle";

    /**
     * Listen for status changes during 'Welcoming mode'.
     * <p>
     * Available status:
     * <ul>
     * <li>{@link OnWelcomingModeStatusChangedListener#ACTIVE}</li>
     * <li>{@link OnWelcomingModeStatusChangedListener#IDLE}</li>
     * </ul>
     *
     * @param status Current status.
     */
    void onWelcomingModeStatusChanged(@WelcomingModeStatus String status);

    @StringDef({ACTIVE, IDLE})
    @Retention(RetentionPolicy.SOURCE)
    @interface WelcomingModeStatus {
    }
}
