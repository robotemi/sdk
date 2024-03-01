package com.robotemi.sdk

import android.os.Parcelable
import androidx.annotation.IntRange
import kotlinx.parcelize.Parcelize

@Parcelize
data class SttRequest(
    val languages: List<SttLanguage> = listOf(),
    @IntRange(from = 0, to = MAX_TIMEOUT) val timeout: Int = 0,
    val multipleConversation: Boolean = false
): Parcelable {
    companion object {
        const val MAX_TIMEOUT = 120L
    }
}