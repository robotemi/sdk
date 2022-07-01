package com.robotemi.sdk.face

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONException
import org.json.JSONObject

data class ContactModel @JvmOverloads constructor(
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "",
    val imageKey: String = "",
    val description: String = "",
    val userId: String = "",
    val age: Int = 0,
    val visitor: Boolean = false,
    val similarity: Double = 0.0
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

    override fun toString(): String {
        return "ContactModel(firstName=$firstName, lastName=$lastName, gender=$gender, imageKey=$imageKey, description=$description, userId=$userId)"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ContactModel> = object : Parcelable.Creator<ContactModel> {
            override fun createFromParcel(source: Parcel): ContactModel = ContactModel(source)
            override fun newArray(size: Int): Array<ContactModel?> = arrayOfNulls(size)
        }

        const val JSON_KEY_DESCRIPTION = "description"
        const val JSON_KEY_USER_ID = "userId"
        const val JSON_KEY_AGE = "age"
        const val JSON_KEY_VISITOR = "visitor"
        const val JSON_KEY_SIMILARITY = "similarity"
    }
}

/**
 * DEV notes
 *
 * ContactModel is passed through AIDL as a list of parcelable.
 * By doing so we cannot add more fields into it while backward compatibility
 * So the solution is wrap new fields into json and carried by description.
 *
 */
internal fun ContactModel.compatible(): ContactModel {
    if (this.description.isBlank()) return this
    val json = JSONObject(this.description)
    val desc = try {
        json.getString(ContactModel.JSON_KEY_DESCRIPTION)
    } catch (e: JSONException) {
        this.description
    }
    val userId = try {
        json.getString(ContactModel.JSON_KEY_USER_ID)
    } catch (e: JSONException) {
        ""
    }
    val age: Int = try {
        json.optInt(ContactModel.JSON_KEY_AGE, 0)
    } catch (e: JSONException) {
        0
    }
    val visitor: Boolean = try {
        json.optBoolean(ContactModel.JSON_KEY_VISITOR, false)
    } catch (e: JSONException) {
        false
    }
    val similarity: Double = try {
        json.optDouble(ContactModel.JSON_KEY_SIMILARITY, 0.0)
    } catch (e: JSONException) {
        0.0
    }
    return this.copy(description = desc, userId = userId, age = age, visitor = visitor, similarity = similarity)
}