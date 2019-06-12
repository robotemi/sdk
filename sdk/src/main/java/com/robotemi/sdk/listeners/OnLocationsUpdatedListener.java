package com.robotemi.sdk.listeners;

import java.util.List;

public interface OnLocationsUpdatedListener {

    /**
     * Called when locations were changed.
     *
     */
    void onLocationsUpdated(List<String> locations);
}
