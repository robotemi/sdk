package com.robotemi.sdk.map

import android.os.Parcel
import android.os.Parcelable

data class MapModel(
    val id: String,
    val name: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<MapModel> {
        override fun createFromParcel(parcel: Parcel): MapModel {
            return MapModel(parcel)
        }

        override fun newArray(size: Int): Array<MapModel?> {
            return arrayOfNulls(size)
        }
    }

}