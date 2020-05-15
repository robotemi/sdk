package com.robotemi.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecentCallModel(
    val userId: String,
    val timestamp: Long?,
    val sessionId: String,
    val callType: Int
) : Parcelable
