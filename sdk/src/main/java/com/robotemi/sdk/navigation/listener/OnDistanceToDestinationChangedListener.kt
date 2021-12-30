package com.robotemi.sdk.navigation.listener

interface OnDistanceToDestinationChangedListener {

    /**
     * This function will be called when the left distance to the
     * destination changes for go-to.
     *
     * @param location location name of the destination
     * @param distance the left distance to the destination
     */
    fun onDistanceToDestinationChanged(location: String, distance: Float)
}