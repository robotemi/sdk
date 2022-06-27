//[sdk](../../../index.md)/[com.robotemi.sdk.exception](../index.md)/[SdkException](index.md)

# SdkException

[androidJvm]\
data class [SdkException](index.md)(var code: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), var message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : Parcelable

## Constructors

| | |
|---|---|
| [SdkException](-sdk-exception.md) | [androidJvm]<br>fun [SdkException](-sdk-exception.md)(source: Parcel) |
| [SdkException](-sdk-exception.md) | [androidJvm]<br>fun [SdkException](-sdk-exception.md)(code: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

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
| [code](code.md) | [androidJvm]<br>var [code](code.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [message](message.md) | [androidJvm]<br>var [message](message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
