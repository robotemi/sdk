//[sdk](../../../index.md)/[com.robotemi.sdk.model](../index.md)/[RecentCallModel](index.md)

# RecentCallModel

[androidJvm]\
data class [RecentCallModel](index.md)(val userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val timestamp: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, val sessionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val callType: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : Parcelable

## Constructors

| | |
|---|---|
| [RecentCallModel](-recent-call-model.md) | [androidJvm]<br>fun [RecentCallModel](-recent-call-model.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), timestamp: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, sessionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), callType: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Functions

| Name | Summary |
|---|---|
| [describeContents](index.md#-1578325224%2FFunctions%2F462465411) | [androidJvm]<br>abstract fun [describeContents](index.md#-1578325224%2FFunctions%2F462465411)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](index.md#-1754457655%2FFunctions%2F462465411) | [androidJvm]<br>abstract fun [writeToParcel](index.md#-1754457655%2FFunctions%2F462465411)(p0: Parcel, p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [callType](call-type.md) | [androidJvm]<br>val [callType](call-type.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [sessionId](session-id.md) | [androidJvm]<br>val [sessionId](session-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [timestamp](timestamp.md) | [androidJvm]<br>val [timestamp](timestamp.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? |
| [userId](user-id.md) | [androidJvm]<br>val [userId](user-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
