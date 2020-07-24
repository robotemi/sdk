package com.robotemi.sdk.model


import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.IntDef

data class CallEventModel(
    var sessionId: String,
    @CallType var type: Int,
    @CallState var state: Int
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(sessionId)
        writeInt(type)
        writeInt(state)
    }

    @IntDef(value = [TYPE_INCOMING, TYPE_OUTGOING])
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class CallType

    @IntDef(value = [STATE_STARTED, STATE_ENDED])
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class CallState

    companion object {
        const val TYPE_INCOMING = 0;

        const val TYPE_OUTGOING = 1;

        const val STATE_STARTED = 0;

        const val STATE_ENDED = 1;

        @JvmField
        val CREATOR: Parcelable.Creator<CallEventModel> =
            object : Parcelable.Creator<CallEventModel> {
                override fun createFromParcel(source: Parcel): CallEventModel =
                    CallEventModel(source)

                override fun newArray(size: Int): Array<CallEventModel?> = arrayOfNulls(size)
            }
    }
}
