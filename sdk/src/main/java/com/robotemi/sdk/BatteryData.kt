package com.robotemi.sdk

import android.os.Parcel
import android.os.Parcelable

/**
 * @property battery2Level Supported in 134 version.
 *                         On some temi built from platform, there will be an extra battery dedicated to power the add-on hardware.
 *                         This property will be used to indicate the battery level of it.
 */
data class BatteryData(
    @get: JvmName("getBatteryPercentage")
    val level: Int,
    val isCharging: Boolean,
    private val hasBattery2: Boolean,
    private val _battery2Level: Int,
) : Parcelable {

    val battery2Level: Int?
        get() = if (hasBattery2) _battery2Level else null

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readInt() == 1,
        source.readInt() == 1,
        source.readInt(),
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(level)
        writeInt(if (isCharging) 1 else 0)
        writeInt(if (hasBattery2) 1 else 0)
        writeInt(_battery2Level)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<BatteryData> = object : Parcelable.Creator<BatteryData> {
            override fun createFromParcel(source: Parcel): BatteryData = BatteryData(source)
            override fun newArray(size: Int): Array<BatteryData?> = arrayOfNulls(size)
        }
    }
}