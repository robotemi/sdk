package com.robotemi.sdk.voice

import com.robotemi.sdk.TtsRequest

interface ITtsService {
    fun speak(ttsRequest: TtsRequest)
    fun cancel()
    fun pause()
    fun resume()
}