package com.robotemi.sdk.navigation.model

import androidx.annotation.FloatRange


enum class SpeedLevel(
    val value: String,
    private var floatValue: Float? = null
) {

    HIGH("high"),       // 0.9 m/s
    MEDIUM("medium"),   // 0.7 m/s
    SLOW("slow");       // 0.5 m/s

    internal val floatSpeedLevel: Float
        get() = floatValue ?: 0.0f

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

        /**
         * Added in 136 version, use a float value to control the max goto speed level.
         * It will fallback to predefined speed level is the temi launcher doesn't support custom speed
         *
         * @param floatValue the custom max goto speed value, range from 0.1 to 1.5
         *                   Currently V3 and temi platform might not be able to go as fast as 1.5 m/s
         *                   due to obstacle avoidance and ground surface conditions.
         *                   The max speed might be around 1.2 m/s.
         *                   But in the future the limit might be changed to go faster.
         */
        fun customSpeed(@FloatRange(from = 0.1, to = 1.5) floatValue: Float): SpeedLevel {
            return when {
                floatValue < 0.7f - EPSILON -> SLOW
                floatValue > 0.9f - EPSILON -> HIGH
                else -> MEDIUM
            }.apply { this.floatValue = floatValue.coerceAtLeast(0.1f) }
        }

        // Fix the float precision issue
        private const val EPSILON = 1e-6f
    }
}