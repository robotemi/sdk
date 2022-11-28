package com.robotemi.sdk.permission

import androidx.annotation.IntDef

/**
 * All permissions can be requested for now.
 *
 * @property value The String type value of the Permission.
 * @property isKioskPermission Deprecated.
 */
enum class Permission(val value: String, val isKioskPermission: Boolean) {

    FACE_RECOGNITION("com.robotemi.permission.face_recognition", false),
    MAP("com.robotemi.permission.map", false),
    SETTINGS("com.robotemi.permission.settings", false),
    SEQUENCE("com.robotemi.permission.sequence", false),
    MEETINGS("com.robotemi.permission.meetings", false),
    UNKNOWN("unknown", false);

    companion object {

        /**
         * Permission check result: this is returned by [com.robotemi.sdk.Robot.checkSelfPermission]
         * if the permission has been granted to the given package.
         */
        const val GRANTED = 1

        /**
         * Permission check result: this is returned by [com.robotemi.sdk.Robot.checkSelfPermission]
         * if the permission has not been granted to the given package.
         */
        const val DENIED = 0

        @JvmStatic
        fun valueToEnum(value: String): Permission {
            return when (value) {
                FACE_RECOGNITION.value -> FACE_RECOGNITION
                MAP.value -> MAP
                SETTINGS.value -> SETTINGS
                SEQUENCE.value -> SEQUENCE
                MEETINGS.value -> MEETINGS
                else -> UNKNOWN
            }
        }

        @JvmStatic
        fun isValidPermission(permission: String) = valueToEnum(permission) != UNKNOWN

        @JvmStatic
        fun isKioskPermission(permission: String) = valueToEnum(permission).isKioskPermission
    }

    @IntDef(value = [GRANTED, DENIED])
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class PermissionResult
}