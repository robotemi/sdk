package com.robotemi.sdk.permission

import androidx.annotation.IntDef
import com.robotemi.sdk.permission.Result.Companion.DENIED
import com.robotemi.sdk.permission.Result.Companion.GRANTED

enum class Permission(val value: String, val isKioskPermission: Boolean) {

    FACE_RECOGNITION("com.robotemi.permission.face_recognition", true),
    MAP("com.robotemi.permission.map", true),
    SETTINGS("com.robotemi.permission.settings", true),
    UNKNOWN("unknown", false);

    companion object {
        fun valueToEnum(value: String): Permission {
            return when (value) {
                FACE_RECOGNITION.value -> FACE_RECOGNITION
                MAP.value -> MAP
                SETTINGS.value -> SETTINGS
                else -> UNKNOWN
            }
        }

        fun isValidPermission(permission: String) = valueToEnum(permission) != UNKNOWN

        fun isKioskPermission(permission: String) = valueToEnum(permission).isKioskPermission
    }
}

@IntDef(GRANTED, DENIED)
annotation class Result {
    companion object {
        const val GRANTED = 0
        const val DENIED = -1
    }
}
