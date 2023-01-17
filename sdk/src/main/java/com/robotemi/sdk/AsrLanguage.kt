package com.robotemi.sdk

import java.util.*
import java.util.Locale.*

enum class AsrLanguage(val locale: Locale) {
    EN_US(US),
    ZH_CN(CHINA),
    ZH_HK(Locale("zh", "HK")),
    ZH_TW(TRADITIONAL_CHINESE),
    TH_TH(Locale("th", "TH")),
    IW_IL(Locale("iw", "IL")),
    KO_KR(KOREA),
    JA_JP(JAPAN),
    IN_ID(Locale("in", "ID")),
    DE_DE(GERMANY),
    FR_FR(FRANCE),
    FR_CA(CANADA_FRENCH),
    PT_BR(Locale("pt", "BR")),
    AR_EG(Locale("ar", "EG")),
    CA_ES(Locale("ca", "ES")),
    IT_IT(ITALY),
}