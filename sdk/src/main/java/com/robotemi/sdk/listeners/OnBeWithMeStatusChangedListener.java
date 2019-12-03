package com.robotemi.sdk.listeners;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

public interface OnBeWithMeStatusChangedListener {

    String ABORT = "abort";

    String CALCULATING = "calculating";

    String LOCK = "lock";

    String SEARCH = "search";

    String START = "start";

    String TRACK = "track";

    String OBSTACLE_DETECTED = "obstacle detected";

    /**
     * Listen for status changes during 'beWithMe'.
     * <p>
     * Available statuses:
     * <ul>
     * <li>{@link OnBeWithMeStatusChangedListener#ABORT}</li>
     * <li>{@link OnBeWithMeStatusChangedListener#CALCULATING}</li>
     * <li>{@link OnBeWithMeStatusChangedListener#LOCK}</li>
     * <li>{@link OnBeWithMeStatusChangedListener#SEARCH}</li>
     * <li>{@link OnBeWithMeStatusChangedListener#START}</li>
     * <li>{@link OnBeWithMeStatusChangedListener#TRACK}</li>
     * <li>{@link OnBeWithMeStatusChangedListener#OBSTACLE_DETECTED}</li>
     * </ul>
     *
     * @param status Current status.
     */
    void onBeWithMeStatusChanged(@BeWithMeStatus String status);

    @StringDef({ABORT, CALCULATING, LOCK, SEARCH, START, TRACK, OBSTACLE_DETECTED})
    @Retention(RetentionPolicy.SOURCE)
    @interface BeWithMeStatus {
    }
}
