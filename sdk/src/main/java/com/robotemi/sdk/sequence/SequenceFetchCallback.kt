package com.robotemi.sdk.sequence

interface SequenceFetchCallback {
    fun onSuccess(sequenceModels: List<SequenceModel>)
    fun onFailure(errorMsg: String)
}