package com.robotemi.sdk.navigation.listener

interface OnDistanceToLocationChangedListener {
    fun onDistanceToLocationChanged(distances: Map<String, Float>)
}