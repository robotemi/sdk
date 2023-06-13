package com.robotemi.sdk.telepresence

import android.os.Parcel
import android.os.Parcelable
import com.robotemi.sdk.constants.Platform

data class Participant(
    val peerId: String,
    val platform: Int,
) : Parcelable {

    constructor(peerId: String, platform: Platform) : this(peerId, platform.value)

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(peerId)
        parcel.writeInt(platform)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Participant> {
        override fun createFromParcel(parcel: Parcel): Participant {
            return Participant(parcel)
        }

        override fun newArray(size: Int): Array<Participant?> {
            return arrayOfNulls(size)
        }
    }
}