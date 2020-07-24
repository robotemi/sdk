package com.robotemi.sdk.listeners

import com.robotemi.sdk.model.DetectionData

interface OnDetectionDataChangedListener {
    fun onDetectionDataChanged(detectionData: DetectionData)
}