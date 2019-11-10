package com.robotemi.sdk.listeners

interface OnLocationsUpdatedListener {

    /**
     * Called when locations were changed.
     *
     */
    fun onLocationsUpdated(locations: List<String>)
}
