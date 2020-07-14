// SequenceFetchServiceCallback.aidl
package com.robotemi.sdk.sequence;

import com.robotemi.sdk.sequence.SequenceModel;

interface SequenceFetchServiceCallback {
    void onSuccess(in List<SequenceModel> sequenceModels);
    void onFailure(in String errorMsg);
}
