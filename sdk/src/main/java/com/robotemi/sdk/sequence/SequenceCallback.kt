package com.robotemi.sdk.sequence

interface SequenceCallback {
    fun onSuccess(sequenceModels: List<SequenceModel>)
    fun onFailure(errorMsg: String)
}