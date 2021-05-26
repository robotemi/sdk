package com.robotemi.sdk

import android.os.Parcel
import android.os.Parcelable

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