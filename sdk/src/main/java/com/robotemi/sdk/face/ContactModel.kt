package com.robotemi.sdk.face

import android.os.Parcel
import android.os.Parcelable

data class ContactModel(
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "",
    val imageKey: String = "",
    val description: String = ""
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(firstName)
        writeString(lastName)
        writeString(gender)
        writeString(imageKey)
        writeString(description)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ContactModel> = object : Parcelable.Creator<ContactModel> {
            override fun createFromParcel(source: Parcel): ContactModel = ContactModel(source)
            override fun newArray(size: Int): Array<ContactModel?> = arrayOfNulls(size)
        }
    }
}