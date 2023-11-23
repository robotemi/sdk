package com.robotemi.sdk

enum class SttLanguage(val value: Int) {
    SYSTEM(0),
    EN_US(1),
    ZH_CN(2),
    JA_JP(3),
    KO_KR(4),
    ZH_HK(5),
    ZH_TW(6),
    DE_DE(7),
    TH_TH(8),
    IN_ID(9),
    PT_BR(10),
    AR_EG(11),
    FR_CA(12),
    FR_FR(13),
    ES_ES(14),
    CA_ES(15),
    IW_IL(16),
    IT_IT(17),
    ET_EE(18),
    TR_TR(19);

    companion object {

        @JvmStatic
        fun valueToEnum(value: Int): SttLanguage {
            return when (value) {
                0 -> SYSTEM
                1 -> EN_US
                2 -> ZH_CN
                3 -> JA_JP
                4 -> KO_KR
                5 -> ZH_HK
                6 -> ZH_TW
                7 -> DE_DE
                8 -> TH_TH
                9 -> IN_ID
                10 -> PT_BR
                11 -> AR_EG
                12 -> FR_CA
                13 -> FR_FR
                14 -> ES_ES
                15 -> CA_ES
                16 -> IW_IL
                17 -> IT_IT
                18 -> ET_EE
                19 -> TR_TR
                else -> SYSTEM
            }
        }
    }
}