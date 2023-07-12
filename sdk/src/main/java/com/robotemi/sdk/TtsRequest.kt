package com.robotemi.sdk

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * @param id will remain the same when you create a TtsRequest and when you received it from [onTtsStatusChanged] callback
 */
data class TtsRequest(
    val id: UUID = UUID.randomUUID(),
    val speech: String,
    var packageName: String = "",
    var status: Status = Status.PENDING,
    val drawableBitmap: Bitmap? = null,
    val isShowOnConversationLayer: Boolean,
    val language: Int = 0,
    val showAnimationOnly: Boolean = false,
    val cached: Boolean = false
) : Parcelable {

    /**
     * Use [TtsRequest.create] to create TtsRequest
     */
    private constructor(source: Parcel) : this(
        id = source.readSerializable() as UUID,
        speech = source.readString()!!,
        packageName = source.readString()!!,
        status = with(source.readInt()) { (if (this == -1) null else Status.values()[this])!! },
        drawableBitmap = source.readParcelable(Bitmap::class.java.classLoader),
        isShowOnConversationLayer = source.readByte().toInt() != 0,
        language = source.readInt(),
        showAnimationOnly = source.readByte().toInt() != 0,
        cached = source.readByte().toInt() != 0,
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
        dest.writeInt(language)
        dest.writeByte(if (showAnimationOnly) 1.toByte() else 0.toByte())
        dest.writeByte(if (cached) 1.toByte() else 0.toByte())
    }

    enum class Status {
        PENDING, PROCESSING, STARTED, COMPLETED, ERROR, NOT_ALLOWED, CANCELED
    }

    enum class Language(val value: Int) {
        SYSTEM(0), EN_US(1), ZH_CN(2), ZH_HK(3), ZH_TW(4), TH_TH(5),
        HE_IL(6), KO_KR(7), JA_JP(8), IN_ID(9), ID_ID(10), DE_DE(11),
        FR_FR(12), FR_CA(13), PT_BR(14), AR_EG(15), AR_AE(16), AR_XA(17),
        RU_RU(18), IT_IT(19), PL_PL(20), ES_ES(21), CA_ES(22), HI_IN(23),
        ET_EE(24), TR_TR(25);

        companion object {

            @JvmStatic
            fun valueToEnum(value: Int): Language {
                return when (value) {
                    1 -> EN_US
                    2 -> ZH_CN
                    3 -> ZH_HK
                    4 -> ZH_TW
                    5 -> TH_TH
                    6 -> HE_IL
                    7 -> KO_KR
                    8 -> JA_JP
                    9 -> IN_ID
                    10 -> ID_ID
                    11 -> DE_DE
                    12 -> FR_FR
                    13 -> FR_CA
                    14 -> PT_BR
                    15 -> AR_EG
                    16 -> AR_AE
                    17 -> AR_XA
                    18 -> RU_RU
                    19 -> IT_IT
                    20 -> PL_PL
                    21 -> ES_ES
                    22 -> CA_ES
                    23 -> HI_IN
                    24 -> ET_EE
                    25 -> TR_TR
                    else -> SYSTEM
                }
            }
        }
    }

    companion object {
        @JvmField
        internal val CREATOR: Parcelable.Creator<TtsRequest> = object : Parcelable.Creator<TtsRequest> {
            override fun createFromParcel(source: Parcel): TtsRequest {
                return TtsRequest(source)
            }

            override fun newArray(size: Int): Array<TtsRequest?> {
                return arrayOfNulls(size)
            }
        }

        /**
         * This is the constructor method of TtsRequest
         *
         * @param speech The text to be spoken
         * @param isShowOnConversationLayer true if you want to it with temi conversation view, default as true
         * @param language specify a language to speak, default as [Language.SYSTEM] which will follow system language.
         * @param showAnimationOnly true if you want to show a face animation while the speech is ongoing.
         *                          This only works if there is an assigned interaction animation in temi Settings,
         *                          otherwise it will just display the text on screen without a face animation.
         *                          Set this as true will override `isShowOnConversationLayer` if that value is set to false.
         * @param cached true if you want to have this tts cached. Default as false.
         *               If there is cache, it will be spoken offline.
         *               This is useful for TTS from some sentences you have in the strings.xml.
         */
        @JvmOverloads
        @JvmStatic
        fun create(
            speech: String,
            isShowOnConversationLayer: Boolean = true,
            language: Language = Language.SYSTEM,
            showAnimationOnly: Boolean = false,
            cached: Boolean = false
        ): TtsRequest {
            return TtsRequest(
                speech = speech,
                isShowOnConversationLayer = isShowOnConversationLayer,
                language = language.value,
                showAnimationOnly = showAnimationOnly,
                cached = cached
            )
        }
    }
}