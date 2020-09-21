package com.robotemi.sdk.map

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MapDataModel(
    @SerializedName("Map_Image")
    var mapImage: MapImage
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<MapImage>(MapImage::class.java.classLoader)!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(mapImage, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MapDataModel> = object : Parcelable.Creator<MapDataModel> {
            override fun createFromParcel(source: Parcel): MapDataModel = MapDataModel(source)
            override fun newArray(size: Int): Array<MapDataModel?> = arrayOfNulls(size)
        }
    }
}