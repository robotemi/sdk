package com.robotemi.sdk.map

interface OnMapElementsChangedListener {

    /**
     * @param traversabilityLayers, list of traversability layers
     */
    fun onMapElementsChanged(mapElements: List<Layer>)
}