package com.robotemi.sdk.navigation.model

enum class SafetyLevel(val value: String) {

    HIGH("high"),
    MEDIUM("medium");

    companion object {

        @JvmField
        val DEFAULT = HIGH

        @JvmStatic
        fun valueToEnum(value: String): SafetyLevel {
            return when (value) {
                MEDIUM.value -> MEDIUM
                HIGH.value -> HIGH
                "low" -> MEDIUM // Compatibility for sprint 126 changes.
                else -> DEFAULT
            }
        }
    }
}