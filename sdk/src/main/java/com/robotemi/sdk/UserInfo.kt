package com.robotemi.sdk

import android.os.Parcel
import android.os.Parcelable

/**
 * @param role 0, admin of robot.
 *             1, collaborator of robot
 *             2, guest of robot
 *             3, contact of robot, is registered temi user
 *             10, contact of robot, only for face recognition, cannot be called with [UserInfo.userId].
 */
data class UserInfo(
    val userId: String,
    val name: String,
    val picUrl: String? = "",
    val role: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(name)
        parcel.writeString(picUrl)
        parcel.writeInt(role)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfo> {
        override fun createFromParcel(parcel: Parcel): UserInfo {
            return UserInfo(parcel)
        }

        override fun newArray(size: Int): Array<UserInfo?> {
            return arrayOfNulls(size)
        }
    }
}