package com.robotemi.sdk.map

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MapImage(
    @SerializedName("type_id")
    val typeId: String,
    val rows: Int,
    val cols: Int,
    val dt: String,
    val data: List<Int>
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readInt(),
        source.readInt(),
        source.readString()!!,
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(typeId)
        writeInt(rows)
        writeInt(cols)
        writeString(dt)
        writeList(data)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MapImage> = object : Parcelable.Creator<MapImage> {
            override fun createFromParcel(source: Parcel): MapImage = MapImage(source)
            override fun newArray(size: Int): Array<MapImage?> = arrayOfNulls(size)
        }
    }
}