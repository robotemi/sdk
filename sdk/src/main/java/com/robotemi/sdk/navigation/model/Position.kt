package com.robotemi.sdk.navigation.model

import android.os.Parcel
import android.os.Parcelable

data class Position(
    var x: Float = 0F,
    var y: Float = 0F,
    var yaw: Float = 0F,
    var tiltAngle: Int = 0
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readFloat(),
        source.readFloat(),
        source.readFloat(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeFloat(x)
        writeFloat(y)
        writeFloat(yaw)
        writeInt(tiltAngle)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Position> = object : Parcelable.Creator<Position> {
            override fun createFromParcel(source: Parcel): Position = Position(source)
            override fun newArray(size: Int): Array<Position?> = arrayOfNulls(size)
        }
    }
}
