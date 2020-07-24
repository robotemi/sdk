package com.robotemi.sdk.navigation.model

enum class SpeedLevel(val value: String) {

    HIGH("high"),
    MEDIUM("medium"),
    SLOW("slow");

    companion object {

        @JvmField
        val DEFAULT = HIGH

        @JvmStatic
        fun valueToEnum(value: String): SpeedLevel {
            return when (value) {
                SLOW.value -> SLOW
                MEDIUM.value -> MEDIUM
                HIGH.value -> HIGH
                else -> DEFAULT
            }
        }
    }
}