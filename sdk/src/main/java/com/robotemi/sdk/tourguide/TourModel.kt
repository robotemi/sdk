package com.robotemi.sdk.tourguide

import android.os.Parcel
import android.os.Parcelable

data class TourModel @JvmOverloads constructor(
    val id: String,
    val name: String,
    val language: String,
    val description: String,
    val imageKey: String = "",
    val tags: List<String> = emptyList(),
    private val extras: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        mutableListOf<String>().apply {
            parcel.readList(this, String::class.java.classLoader)
        },
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(language)
        parcel.writeString(description)
        parcel.writeString(imageKey)
        parcel.writeList(tags)
        parcel.writeString(extras)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TourModel> =
            object : Parcelable.Creator<TourModel> {
                override fun createFromParcel(parcel: Parcel): TourModel {
                    return TourModel(parcel)
                }

                override fun newArray(size: Int): Array<TourModel?> {
                    return arrayOfNulls(size)
                }
            }
    }
}

internal fun TourModel.compatible(): TourModel {
    return this
}