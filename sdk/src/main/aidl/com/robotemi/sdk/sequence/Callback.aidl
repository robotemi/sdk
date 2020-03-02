package com.robotemi.sdk.sequence;

import com.robotemi.sdk.sequence.SequenceModel;

interface Callback {
    void onSuccess(in List<SequenceModel> sequenceModelList);

    void onFailure(in String errorMessage);
}