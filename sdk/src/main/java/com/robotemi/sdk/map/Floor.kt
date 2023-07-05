package com.robotemi.sdk.map

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.json.JSONObject


@Keep
data class Floor(
    val id: Int = -1,
    val name: String = "",
    val mapId: String = "",
    private val data: String = ""
) : Parcelable {

    @Suppress("MemberVisibilityCanBePrivate")
    val locations: List<Location>
        get() {
            val locations = JSONObject(data).getString("locations")
            return Gson().fromJson<List<Location>>(
                locations,
                object : TypeToken<List<Location>>() {}.type
            )
        }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(mapId)
        parcel.writeString(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Floor(id=$id, name='$name', mapId='$mapId', locations=$locations)"
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
    @SerializedName("tilt_angle") val tiltAngle: Float = 0f
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString() ?: "",
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(x)
        parcel.writeFloat(y)
        parcel.writeFloat(yaw)
        parcel.writeString(name)
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