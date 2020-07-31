package com.robotemi.sdk

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class TtsRequest(
    val id: UUID = UUID.randomUUID(),
    val speech: String,
    var packageName: String = "",
    var status: Status = Status.PENDING,
    val drawableBitmap: Bitmap? = null,
    val isShowOnConversationLayer: Boolean
) : Parcelable {

    constructor(source: Parcel) : this(
        id = source.readSerializable() as UUID,
        speech = source.readString()!!,
        packageName = source.readString()!!,
        status = with(source.readInt()) { (if (this == -1) null else Status.values()[this])!! },
        drawableBitmap = source.readParcelable(Bitmap::class.java.classLoader),
        isShowOnConversationLayer = source.readByte().toInt() != 0
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as TtsRequest
        return id == that.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "TtsRequest{" +
                "id=" + id +
                ", speech='" + speech + '\'' +
                ", packageName='" + packageName + '\'' +
                ", status=" + status +
                ", isShowOnConversationLayer=" + isShowOnConversationLayer +
                '}'
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeSerializable(id)
        dest.writeString(speech)
        dest.writeString(packageName)
        dest.writeInt(status.ordinal)
        dest.writeParcelable(drawableBitmap, flags)
        dest.writeByte(if (isShowOnConversationLayer) 1.toByte() else 0.toByte())
    }

    enum class Status {
        PENDING, PROCESSING, STARTED, COMPLETED, ERROR, NOT_ALLOWED
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TtsRequest> = object : Parcelable.Creator<TtsRequest> {
            override fun createFromParcel(source: Parcel): TtsRequest? {
                return TtsRequest(source)
            }

            override fun newArray(size: Int): Array<TtsRequest?> {
                return arrayOfNulls(size)
            }
        }

        @JvmStatic
        fun create(speech: String, isShowOnConversationLayer: Boolean): TtsRequest {
            return TtsRequest(
                speech = speech, isShowOnConversationLayer = isShowOnConversationLayer
            )
        }
    }
}