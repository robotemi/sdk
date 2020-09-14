package com.robotemi.sdk.constants

object SdkConstants {
    // metadata key
    const val METADATA_ACTIONS = "com.robotemi.sdk.metadata.ACTIONS"
    const val METADATA_CONTEXTS = "com.robotemi.sdk.metadata.CONTEXTS"
    const val METADATA_HINTS = "com.robotemi.sdk.metadata.HINTS"
    const val METADATA_VISIBILITY = "com.robotemi.sdk.metadata.VISIBILITY"
    const val METADATA_SKILL = "com.robotemi.sdk.metadata.SKILL"
    const val METADATA_KIOSK = "com.robotemi.sdk.metadata.KIOSK"
    const val METADATA_ASSISTANT = "com.robotemi.sdk.metadata.ASSISTANT"
    const val METADATA_UI_MODE = "com.robotemi.sdk.metadata.UI_MODE"
    const val METADATA_UI_FLAG = "com.robotemi.sdk.metadata.UI_FLAG"
    const val METADATA_OPEN_WITHOUT_INTERNET = "com.robotemi.sdk.metadata.OPEN_WITHOUT_INTERNET"
    const val METADATA_OVERRIDE_NLU = "com.robotemi.sdk.metadata.OVERRIDE_NLU"
    const val METADATA_OVERRIDE_STT = "com.robotemi.sdk.metadata.OVERRIDE_STT"
    const val METADATA_PERMISSIONS = "com.robotemi.sdk.metadata.PERMISSIONS"

    /**
     * Show everything.
     * `R.integer.metadata_ui_flag_default`
     */
    const val METADATA_UI_FLAG_DEFAULT = 0

    /**
     * Hide top bar always.
     * `R.integer.metadata_ui_flag_hide_top_bar`
     */
    const val METADATA_UI_FLAG_HIDE_TOP_BAR = 1

    /**
     * All UI elements are visible.
     * If this mode is set other Flags are ignored.
     * `R.integer.metadata_ui_mode_default`
     */
    const val METADATA_UI_MODE_DEFAULT = 0

    /**
     * All UI elements are hidden.
     * If this mode is set other Flags are ignored.
     * `R.integer.metadata_ui_mode_fullscreen`
     */
    const val METADATA_UI_MODE_FULLSCREEN = 1

    /**
     * UI elements can be shown/hidden when requested.
     * This mode can be used in conjunction with Flags.
     * `R.integer.metadata_ui_mode_immersive`
     */
    const val METADATA_UI_MODE_IMMERSIVE = 2

    /**
     * All UI elements are visible.
     * If this mode is set other Flags are ignored.
     * `R.integer.metadata_ui_mode_blend`
     */
    const val METADATA_UI_MODE_BLEND = 3

    const val METADATA_UI_MODE_HIDE_ALL_TOP_BAR_ELEMENTS = 4

    const val PLAY = -1
    const val PAUSE = -2
    const val NEXT = -3
    const val BACK = -4
    const val EXTRA_NLP_RESULT = "com.robotemi.sdk.extra.NLP_RESULT"
    const val TEMI_USA = "com.roboteam.teamy.usa"
    const val TEMI_CHINA = "com.roboteam.teamy.china"

    const val DETECTION_DISTANCE_DEFAULT = 0.8F

    const val PROVIDER_AUTHORITY = "com.robotemi.sdk.provider"
    const val PROVIDER_PARAMETER_MEDIA_KEY = "mediaKey"

    const val LOCATION_HOME_BASE = "home base"
}

enum class ContentType(val path: String) {
    FACE_RECOGNITION_IMAGE("face_recognition_image"),
    MAP_DATA_IMAGE("map_data_image")
}