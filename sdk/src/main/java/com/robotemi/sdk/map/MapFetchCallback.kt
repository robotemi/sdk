package com.robotemi.sdk.map

interface MapFetchCallback {
    fun onSuccess(mapDataModel: MapDataModel)
    fun onFailure(errorMsg: String)
}