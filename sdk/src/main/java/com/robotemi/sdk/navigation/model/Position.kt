package com.robotemi.sdk.navigation.model

import android.os.Parcel
import android.os.Parcelable

/**
    @param isInMapArea, added in 136 version to indicate if the robot is in the map area. Nullable for backward compatibility.
 */
data class Position(
    var x: Float = 0F,
    var y: Float = 0F,
    var yaw: Float = 0F,
    var tiltAngle: Int = 0,
    var isInMapArea: Boolean? = null,
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readFloat(),
        source.readFloat(),
        source.readFloat(),
        source.readInt(),
        source.readInt().let {
            when (it) {
                1 -> true
                -1 -> false
                else -> null
            }
        } ,
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeFloat(x)
        writeFloat(y)
        writeFloat(yaw)
        writeInt(tiltAngle)
        writeInt(if(isInMapArea == true) 1 else if(isInMapArea == false) -1 else 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Position> = object : Parcelable.Creator<Position> {
            override fun createFromParcel(source: Parcel): Position = Position(source)
            override fun newArray(size: Int): Array<Position?> = arrayOfNulls(size)
        }
    }
}
