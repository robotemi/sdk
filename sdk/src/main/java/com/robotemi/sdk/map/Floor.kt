package com.robotemi.sdk.map

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class Floor(
    val index: Int, // for sorting
    val name: String = "", // floor name
    val mapId: String? = null, // robox map id
//    val mapElements: List<Layer> = emptyList(), // virtual wall, green path
    val locations: List<Location> = listOf(),
    val mongoId: String = "",
//    val createdAt: Long = System.currentTimeMillis(),
//    val lastEdited: Long = System.currentTimeMillis(),
    val id: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString(),
//        parcel.createTypedArrayList(Layer) ?: emptyList(),
        parcel.createTypedArrayList(Location) ?: emptyList(),
        parcel.readString() ?: "",
//        parcel.readLong(),
//        parcel.readLong(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(index)
        parcel.writeString(name)
        parcel.writeString(mapId)
//        parcel.writeTypedList(mapElements)
        parcel.writeTypedList(locations)
        parcel.writeString(mongoId)
//        parcel.writeLong(createdAt)
//        parcel.writeLong(lastEdited)
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Floor> {
        override fun createFromParcel(parcel: Parcel): Floor {
            return Floor(parcel)
        }

        override fun newArray(size: Int): Array<Floor?> {
            return arrayOfNulls(size)
        }
    }
}

@Keep
data class Location(
    val x: Float,
    val y: Float,
    val yaw: Float,
    val name: String,
    val created: Long = System.currentTimeMillis(),
    val lastUsed: Long = 0L,
    val useNumber: Int = 0,
    @SerializedName("tilt_angle") val tiltAngle: Float = 0f
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(x)
        parcel.writeFloat(y)
        parcel.writeFloat(yaw)
        parcel.writeString(name)
        parcel.writeLong(created)
        parcel.writeLong(lastUsed)
        parcel.writeInt(useNumber)
        parcel.writeFloat(tiltAngle)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }

}