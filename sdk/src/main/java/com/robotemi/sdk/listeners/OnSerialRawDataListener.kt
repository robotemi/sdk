package com.robotemi.sdk.listeners;

interface OnSerialRawDataListener {

    /**
     * Raw serial data from hardware components on temi GO.
     *
     */
    fun onSerialRawData(data: ByteArray)
}
