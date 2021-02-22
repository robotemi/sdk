package com.robotemi.sdk.listeners

interface OnDisabledFeatureListUpdatedListener {
    fun onDisabledFeatureListUpdated(disabledFeatureList: List<String>)

    companion object {
        const val BE_WITH_ID = "com.roboteam.teamy.robox.data.api.info.ready::BE_WITH"
        const val GO_TO_ID = "com.roboteam.teamy.robox.data.api.info.ready::GO_TO"
        const val HARD_BUTTON_ID = "com.roboteam.teamy.robox.data.api.info.ready::HARD_BUTTON"
        const val MIC_ID = "com.roboteam.teamy.robox.data.api.info.ready::MIC"
        const val MQTT_ID = "com.roboteam.teamy.robox.data.api.info.ready::MQTT"
        const val SPEAKER_ID = "com.roboteam.teamy.robox.data.api.info.ready::SPEAKER"
        const val ST_CLEAN_ID = "com.roboteam.teamy.robox.data.api.info.ready::ST_CLEAN"
        const val ST_VALID_ID = "com.roboteam.teamy.robox.data.api.info.ready::ST_VALID"
        const val WAKEUP_ID = "com.roboteam.teamy.robox.data.api.info.ready::WAKEUP"
    }
}