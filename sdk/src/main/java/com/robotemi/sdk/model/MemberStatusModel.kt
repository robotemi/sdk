package com.robotemi.sdk.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.IntDef

data class MemberStatusModel(
    val memberId: String,
    @Status val mobileStatus: Int = STATUS_OFFLINE,
    @Status val centerStatus: Int = STATUS_OFFLINE
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    )

    @IntDef(value = [STATUS_ONLINE, STATUS_OFFLINE, STATUS_BUSY])
    annotation class Status

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(memberId)
        parcel.writeInt(mobileStatus)
        parcel.writeInt(centerStatus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemberStatusModel> {

        const val STATUS_ONLINE = 0
        const val STATUS_OFFLINE = 1
        const val STATUS_BUSY = 2

        override fun createFromParcel(parcel: Parcel): MemberStatusModel {
            return MemberStatusModel(parcel)
        }

        override fun newArray(size: Int): Array<MemberStatusModel?> {
            return arrayOfNulls(size)
        }
    }
}