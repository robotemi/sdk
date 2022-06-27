//[sdk](../../../index.md)/[com.robotemi.sdk.telepresence](../index.md)/[CallState](index.md)

# CallState

[androidJvm]\
open class [CallState](index.md) : Parcelable

## Constructors

| | |
|---|---|
| [CallState](-call-state.md) | [androidJvm]<br>open fun [CallState](-call-state.md)(@NonNullsessionId: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), state: [CallState.State](-state/index.md)) |

## Types

| Name | Summary |
|---|---|
| [State](-state/index.md) | [androidJvm]<br>enum [State](-state/index.md) |

## Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | [androidJvm]<br>open fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toString](to-string.md) | [androidJvm]<br>@NonNull<br>open fun [toString](to-string.md)(): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open fun [writeToParcel](write-to-parcel.md)(dest: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [CONTENTS_FILE_DESCRIPTOR](index.md#-255210448%2FProperties%2F462465411) | [androidJvm]<br>val [CONTENTS_FILE_DESCRIPTOR](index.md#-255210448%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [CREATOR](-c-r-e-a-t-o-r.md) | [androidJvm]<br>val [CREATOR](-c-r-e-a-t-o-r.md): Parcelable.Creator&lt;[CallState](index.md)&gt; |
| [PARCELABLE_WRITE_RETURN_VALUE](index.md#-865205281%2FProperties%2F462465411) | [androidJvm]<br>val [PARCELABLE_WRITE_RETURN_VALUE](index.md#-865205281%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [sessionId](session-id.md) | [androidJvm]<br>@NonNull<br>@get:NonNull<br>val [sessionId](session-id.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [state](state.md) | [androidJvm]<br>val [state](state.md): [CallState.State](-state/index.md) |
