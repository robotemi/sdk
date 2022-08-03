package com.robotemi.sdk.voice.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import com.robotemi.sdk.constants.Gender

/**
 * @param gender only female and male can be used as parameter
 * @param speed 0.5 - 2.0   stepping by 0.1, default 1.0
 * @param pitch -10 - 10    stepping by 1, default 0
 */
data class TtsVoice(
    var gender: Gender = Gender.FEMALE,
    @FloatRange(from = 0.5, to = 2.0)
    var speed: Float = 1.0F,
    @IntRange(from = -10, to = 10)
    var pitch: Int = 0
) : Parcelable {

    private constructor(source: Parcel) : this(
        Gender.valueOf(source.readString() ?: ""),
        source.readFloat(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(gender.name)
        writeFloat(speed)
        writeInt(pitch)
    }

    internal companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TtsVoice> = object : Parcelable.Creator<TtsVoice> {
            override fun createFromParcel(source: Parcel): TtsVoice = TtsVoice(source)
            override fun newArray(size: Int): Array<TtsVoice?> = arrayOfNulls(size)
        }
    }
}