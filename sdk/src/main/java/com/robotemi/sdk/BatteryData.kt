package com.robotemi.sdk

import android.os.Parcel
import android.os.Parcelable

data class BatteryData(
    @get: JvmName("getBatteryPercentage")
    val level: Int,
    val isCharging: Boolean
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt() == 1
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(level)
        writeInt((if (isCharging) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<BatteryData> = object : Parcelable.Creator<BatteryData> {
            override fun createFromParcel(source: Parcel): BatteryData = BatteryData(source)
            override fun newArray(size: Int): Array<BatteryData?> = arrayOfNulls(size)
        }
    }
}