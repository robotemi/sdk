//[sdk](../../../index.md)/[com.robotemi.sdk](../index.md)/[TtsRequest](index.md)

# TtsRequest

[androidJvm]\
data class [TtsRequest](index.md)(val id: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html) = UUID.randomUUID(), val speech: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), var packageName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var status: [TtsRequest.Status](-status/index.md) = Status.PENDING, val drawableBitmap: Bitmap? = null, val isShowOnConversationLayer: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val language: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val showAnimationOnly: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : Parcelable

## Constructors

| | |
|---|---|
| [TtsRequest](-tts-request.md) | [androidJvm]<br>fun [TtsRequest](-tts-request.md)(source: Parcel) |
| [TtsRequest](-tts-request.md) | [androidJvm]<br>fun [TtsRequest](-tts-request.md)(id: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html) = UUID.randomUUID(), speech: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), packageName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, status: [TtsRequest.Status](-status/index.md) = Status.PENDING, drawableBitmap: Bitmap? = null, isShowOnConversationLayer: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), language: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, showAnimationOnly: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |
| [Language](-language/index.md) | [androidJvm]<br>enum [Language](-language/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[TtsRequest.Language](-language/index.md)&gt; |
| [Status](-status/index.md) | [androidJvm]<br>enum [Status](-status/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[TtsRequest.Status](-status/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | [androidJvm]<br>open override fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [equals](equals.md) | [androidJvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | [androidJvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open override fun [writeToParcel](write-to-parcel.md)(dest: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [drawableBitmap](drawable-bitmap.md) | [androidJvm]<br>val [drawableBitmap](drawable-bitmap.md): Bitmap? = null |
| [id](id.md) | [androidJvm]<br>val [id](id.md): [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html) |
| [isShowOnConversationLayer](is-show-on-conversation-layer.md) | [androidJvm]<br>val [isShowOnConversationLayer](is-show-on-conversation-layer.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [language](language.md) | [androidJvm]<br>val [language](language.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0 |
| [packageName](package-name.md) | [androidJvm]<br>var [packageName](package-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [showAnimationOnly](show-animation-only.md) | [androidJvm]<br>val [showAnimationOnly](show-animation-only.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [speech](speech.md) | [androidJvm]<br>val [speech](speech.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [status](status.md) | [androidJvm]<br>var [status](status.md): [TtsRequest.Status](-status/index.md) |
