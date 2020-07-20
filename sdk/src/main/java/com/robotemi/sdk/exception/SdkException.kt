package com.robotemi.sdk.exception

import android.os.Parcel
import android.os.Parcelable

data class SdkException(
    var code: Int,
    var message: String
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(code)
        writeString(message)
    }

    companion object {

        const val CODE_ILLEGAL_ARGUMENT = 400
        const val CODE_PERMISSION_DENIED = 403
        const val CODE_OPERATION_CONFLICT = 409

        const val CODE_LAUNCHER_ERROR = 500

        @JvmField
        val CREATOR: Parcelable.Creator<SdkException> = object : Parcelable.Creator<SdkException> {
            override fun createFromParcel(source: Parcel): SdkException = SdkException(source)
            override fun newArray(size: Int): Array<SdkException?> = arrayOfNulls(size)
        }

        @JvmStatic
        fun permissionDenied(permission: String) =
            SdkException(CODE_PERMISSION_DENIED, "Required permission: $permission")

        @JvmStatic
        fun illegalArgument(msg: String) = SdkException(CODE_ILLEGAL_ARGUMENT, msg)

        @JvmStatic
        fun operationConflict(msg: String) = SdkException(CODE_OPERATION_CONFLICT, msg)

        @JvmStatic
        fun launcherError(msg: String) = SdkException(CODE_LAUNCHER_ERROR, msg)
    }
}