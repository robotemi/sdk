package com.robotemi.sdk.navigation.listener

interface OnDistanceToDestinationChangedListener {

    /**
     * This function will be called when the distance left to the
     * destination changes in a go-to session.
     *
     * @param location location name of the destination
     * @param distance distance to the destination
     */
    fun onDistanceToDestinationChanged(location: String, distance: Float)
}