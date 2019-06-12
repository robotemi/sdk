package com.robotemi.sdk.listeners;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

public interface OnGoToLocationStatusChangedListener {

    String START = "start";

    String CALCULATING = "calculating";

    String GOING = "going";

    String COMPLETE = "complete";

    String ABORT = "abort";

    /**
     * Listen for status changes during 'go to location'.
     * <p>
     * Available statuses:
     * <ul>
     * <li>{@link OnGoToLocationStatusChangedListener#START}</li>
     * <li>{@link OnGoToLocationStatusChangedListener#CALCULATING}</li>
     * <li>{@link OnGoToLocationStatusChangedListener#GOING}</li>
     * <li>{@link OnGoToLocationStatusChangedListener#COMPLETE}</li>
     * <li>{@link OnGoToLocationStatusChangedListener#ABORT}</li>
     * </ul>
     *
     * @param location Location of GoTo response.
     * @param status Current status.
     */
    void onGoToLocationStatusChanged(String location, @GoToLocationStatus String status);

    @StringDef({START, CALCULATING, GOING, COMPLETE, ABORT})
    @Retention(RetentionPolicy.SOURCE)
    @interface GoToLocationStatus {
    }
}
