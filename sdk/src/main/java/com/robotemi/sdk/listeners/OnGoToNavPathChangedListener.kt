package com.robotemi.sdk.listeners

import com.robotemi.sdk.map.LayerPose


interface OnGoToNavPathChangedListener {

    /**
     * The planned path to go to destination.
     * This callback will be triggered frequently, about every 1 second during robot goto session.
     * Only x, y matters, theta will always be 0.
     */
    fun onGoToNavPathChanged(path: List<LayerPose>)

}