package com.robotemi.sdk.listeners

interface OnRobotDragStateChangedListener {

    /**
     *
     *Called when the drag state of the robot changes(true = being dragged,false = not dragged)
     */
    fun onRobotDragStateChanged(isDragged: Boolean)
}