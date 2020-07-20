package com.robotemi.sdk.exception

interface OnSdkExceptionListener {
    fun onSdkError(sdkException: SdkException)
}