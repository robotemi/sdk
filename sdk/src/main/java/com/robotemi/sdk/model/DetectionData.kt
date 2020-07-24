package com.robotemi.sdk.model

import android.os.Parcel
import android.os.Parcelable

data class DetectionData(
    val angle: Double,
    val distance: Double,
    val isDetected: Boolean
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readDouble(),
        source.readDouble(),
        1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeDouble(angle)
        writeDouble(distance)
        writeInt((if (isDetected) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DetectionData> =
            object : Parcelable.Creator<DetectionData> {
                override fun createFromParcel(source: Parcel): DetectionData = DetectionData(source)
                override fun newArray(size: Int): Array<DetectionData?> = arrayOfNulls(size)
            }
    }
}