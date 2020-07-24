package com.robotemi.sdk.navigation.listener

import com.robotemi.sdk.navigation.model.Position

interface OnCurrentPositionChangedListener {
    fun onCurrentPositionChanged(position: Position)
}