package com.robotemi.sdk.sequence

import android.os.Parcel
import android.os.Parcelable

data class SequenceModel(
    val id: String,
    val name: String,
    val description: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SequenceModel> {
        override fun createFromParcel(parcel: Parcel): SequenceModel {
            return SequenceModel(parcel)
        }

        override fun newArray(size: Int): Array<SequenceModel?> {
            return arrayOfNulls(size)
        }
    }
}