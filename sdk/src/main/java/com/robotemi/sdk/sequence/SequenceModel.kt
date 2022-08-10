package com.robotemi.sdk.sequence

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONException
import org.json.JSONObject

data class SequenceModel @JvmOverloads constructor(
    val id: String,
    val name: String,
    val description: String,
    val imageKey: String = "",
    val tags: List<String> = emptyList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SequenceModel> =
            object : Parcelable.Creator<SequenceModel> {
                override fun createFromParcel(parcel: Parcel): SequenceModel {
                    return SequenceModel(parcel)
                }

                override fun newArray(size: Int): Array<SequenceModel?> {
                    return arrayOfNulls(size)
                }
            }

        const val JSON_KEY_DESCRIPTION = "description"
        const val JSON_KEY_IMAGE_KEY = "imageKey"
        const val JSON_KEY_TAGS = "tags"
    }
}

internal fun SequenceModel.compatible(): SequenceModel {
    if (this.description.isBlank()) return this
    val json = JSONObject(this.description)
    val desc = try {
        json.getString(SequenceModel.JSON_KEY_DESCRIPTION)
    } catch (e: JSONException) {
        ""
    }
    val imgKey = try {
        json.getString(SequenceModel.JSON_KEY_IMAGE_KEY)
    } catch (e: JSONException) {
        ""
    }
    val tagList = try {
        val jsonArray = json.optJSONArray(SequenceModel.JSON_KEY_TAGS)
        if (jsonArray == null) {
            emptyList<String>()
        } else {
            val result = mutableListOf<String>()
            for (i in 0 until jsonArray.length()) {
                result.add(jsonArray.getString(i))
            }
            result
        }
    } catch (e: JSONException) {
        emptyList<String>()
    }
    return this.copy(description = desc, imageKey = imgKey, tags = tagList)
}