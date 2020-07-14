package com.robotemi.sdk.map;

import com.robotemi.sdk.map.MapDataModel;

interface MapFetchServiceCallback {
    void onSuccess(in MapDataModel mapDataModel);
    void onFailure(in String errorMsg);
}
