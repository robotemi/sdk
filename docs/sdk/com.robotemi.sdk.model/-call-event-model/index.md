//[sdk](../../../index.md)/[com.robotemi.sdk.model](../index.md)/[CallEventModel](index.md)

# CallEventModel

[androidJvm]\
data class [CallEventModel](index.md)(var sessionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), var type: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), var state: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : Parcelable

## Constructors

| | |
|---|---|
| [CallEventModel](-call-event-model.md) | [androidJvm]<br>fun [CallEventModel](-call-event-model.md)(source: Parcel) |
| [CallEventModel](-call-event-model.md) | [androidJvm]<br>fun [CallEventModel](-call-event-model.md)(sessionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), state: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Types

| Name | Summary |
|---|---|
| [CallState](-call-state/index.md) | [androidJvm]<br>annotation class [CallState](-call-state/index.md) |
| [CallType](-call-type/index.md) | [androidJvm]<br>annotation class [CallType](-call-type/index.md) |
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | [androidJvm]<br>open override fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open override fun [writeToParcel](write-to-parcel.md)(dest: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [sessionId](session-id.md) | [androidJvm]<br>var [sessionId](session-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [state](state.md) | [androidJvm]<br>var [state](state.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [type](type.md) | [androidJvm]<br>var [type](type.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
