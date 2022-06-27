//[sdk](../../../index.md)/[com.robotemi.sdk](../index.md)/[BatteryData](index.md)

# BatteryData

[androidJvm]\
data class [BatteryData](index.md)(val level: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val isCharging: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) : Parcelable

## Constructors

| | |
|---|---|
| [BatteryData](-battery-data.md) | [androidJvm]<br>fun [BatteryData](-battery-data.md)(source: Parcel) |
| [BatteryData](-battery-data.md) | [androidJvm]<br>fun [BatteryData](-battery-data.md)(level: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), isCharging: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | [androidJvm]<br>open override fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open override fun [writeToParcel](write-to-parcel.md)(dest: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [isCharging](is-charging.md) | [androidJvm]<br>val [isCharging](is-charging.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [level](level.md) | [androidJvm]<br>@get:[JvmName](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-name/index.html)(name = &quot;getBatteryPercentage&quot;)<br>val [level](level.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
