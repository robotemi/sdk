package com.robotemi.sdk.telepresence

import androidx.annotation.IntDef
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.Date


data class LinkBasedMeeting(
    @SerializedName("topic") val topic: String,
    @SerializedName("availability") val availability: Availability,
    @SerializedName("limit") val limit: Limit,
    @SerializedName("permission") val permission: Permission,
    @SerializedName("security") val security: Security,
) {

    @Keep
    data class Availability(
        @SerializedName("start") val start: Date? = null,
        @SerializedName("end") val end: Date? = null,
        @SerializedName("always") val always: Boolean
    )

    @Keep
    data class Limit(
        @SerializedName("callDuration") @CallDuration val callDuration:Int,
        @SerializedName("usageLimit") @UsageLimit val usageLimit: Int,
    )

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(
        CallDuration.NO_LIMIT,
        CallDuration.MINUTE_5,
        CallDuration.MINUTE_10,
        CallDuration.MINUTE_20,
        CallDuration.MINUTE_40,
        CallDuration.MINUTE_60,
        CallDuration.MINUTE_90,
        CallDuration.MINUTE_120,
        CallDuration.MINUTE_180,
    )
    annotation class CallDuration {
        companion object {
            const val NO_LIMIT = 0
            const val MINUTE_5 = 5
            const val MINUTE_10 = 10
            const val MINUTE_20 = 20
            const val MINUTE_40 = 40
            const val MINUTE_60 = 60
            const val MINUTE_90 = 90
            const val MINUTE_120 = 120
            const val MINUTE_180 = 180
        }
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(
        UsageLimit.NO_LIMIT,
        UsageLimit.SINGLE_USE,
    )
    annotation class UsageLimit {
        companion object {
            const val NO_LIMIT = 0
            const val SINGLE_USE = 1
        }
    }

    @Keep
    data class Permission(
        @SerializedName("control") val control: Control = Control(),
        @SerializedName("addNotes") val addNotes: Boolean = true
    ) {
        companion object {
            val DEFAULT = Permission()
            val DISABLE_ROBOT_INTERACTION = Permission(control = Control(blockRobotInteraction = true))
        }
    }

    /**
     * @param blockRobotInteraction, added in 132 version. Default as false.
     * It will disable some robot ui in the meeting to prevent unwanted user interactions on the robot.
     * e.g. Not allowing user to hang up from the robot.
     */
    @Keep
    data class Control(
        @SerializedName("manualDrive") val manualDrive: Boolean = true,
        @SerializedName("screenControl") val screenControl: Boolean = true,
        @SerializedName("locations") val locations: Locations = Locations(),
        @SerializedName("blockRobotInteraction") val blockRobotInteraction: Boolean = false
    )

    @Keep
    data class Locations(
        @SerializedName("enabled") val enabled: Boolean = true,
        @SerializedName("all") val all: Boolean = true,
        @SerializedName("list") val list: List<String> = listOf()
    )

    /**
     * @param password, digits only. can be
     */
    @Keep
    data class Security(
        @SerializedName("passcode") val password: String = "",
        @SerializedName("enablePassword") val hasPassword: Boolean = false
    )
}

