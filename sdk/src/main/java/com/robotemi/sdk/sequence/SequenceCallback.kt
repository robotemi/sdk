package com.robotemi.sdk.sequence

import java.lang.Exception

interface SequenceCallback {
    fun onSuccess(sequenceList: MutableList<SequenceModel>)
    fun onFailure(e: Exception)
}