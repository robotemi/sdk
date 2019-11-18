package com.robotemi.sdk.constants;

public final class SdkConstants {

    public static final String METADATA_ACTIONS = "com.robotemi.sdk.metadata.ACTIONS";

    public static final String METADATA_CONTEXTS = "com.robotemi.sdk.metadata.CONTEXTS";

    public static final String METADATA_HINTS = "com.robotemi.sdk.metadata.HINTS";

    public static final String METADATA_VISIBILITY = "com.robotemi.sdk.metadata.VISIBILITY";

    public static final String METADATA_SKILL = "com.robotemi.sdk.metadata.SKILL";

    public static final String METADATA_KIOSK = "com.robotemi.sdk.metadata.KIOSK";

    public static final String METADATA_ASSISTANT = "com.robotemi.sdk.metadata.ASSISTANT";

    public static final String METADATA_UI_MODE = "com.robotemi.sdk.metadata.UI_MODE";

    public static final String METADATA_UI_FLAG = "com.robotemi.sdk.metadata.UI_FLAG";

    public static final String METADATA_OPEN_WITHOUT_INTERNET = "com.robotemi.sdk.metadata.OPEN_WITHOUT_INTERNET";

    /**
     * Show everything.
     * {@code R.integer.metadata_ui_flag_default}
     *
     */
    public static final int METADATA_UI_FLAG_DEFAULT = 0;

    /**
     * Hide top bar always.
     * {@code R.integer.metadata_ui_flag_hide_top_bar}
     */
    public static final int METADATA_UI_FLAG_HIDE_TOP_BAR = 1;

    /**
     * All UI elements are visible.
     * If this mode is set other Flags are ignored.
     * {@code R.integer.metadata_ui_mode_default}
     */
    public static final int METADATA_UI_MODE_DEFAULT = 0;

    /**
     * All UI elements are hidden.
     * If this mode is set other Flags are ignored.
     * {@code R.integer.metadata_ui_mode_fullscreen}
     */
    public static final int METADATA_UI_MODE_FULLSCREEN = 1;

    /**
     * UI elements can be shown/hidden when requested.
     * This mode can be used in conjunction with Flags.
     * {@code R.integer.metadata_ui_mode_immersive}
     */
    public static final int METADATA_UI_MODE_IMMERSIVE = 2;

    /**
     * All UI elements are visible.
     * If this mode is set other Flags are ignored.
     * {@code R.integer.metadata_ui_mode_blend}
     */
    public static final int METADATA_UI_MODE_BLEND = 3;

    public static final int PLAY = -1;

    public static final int PAUSE = -2;

    public static final int NEXT = -3;

    public static final int BACK = -4;

    public static final String EXTRA_NLP_RESULT = "com.robotemi.sdk.extra.NLP_RESULT";

    public static final String TEMI_USA = "com.roboteam.teamy.usa";

    public static final String TEMI_CHINA = "com.roboteam.teamy.china";

    private SdkConstants() {
        super();
        // private constructor to prevent instantiation
    }
}
