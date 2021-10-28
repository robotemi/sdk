[sdk](../../index.md) / [com.robotemi.sdk](../index.md) / [TtsRequest](./index.md)

# TtsRequest

`data class TtsRequest : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

### Types

| Name | Summary |
|---|---|
| [Language](-language/index.md) | `enum class Language` |
| [Status](-status/index.md) | `enum class Status` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `TtsRequest(source: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`)`<br>`TtsRequest(id: `[`UUID`](https://developer.android.com/reference/java/util/UUID.html)` = UUID.randomUUID(), speech: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, packageName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "", status: `[`TtsRequest.Status`](-status/index.md)` = Status.PENDING, drawableBitmap: `[`Bitmap`](https://developer.android.com/reference/android/graphics/Bitmap.html)`? = null, isShowOnConversationLayer: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, language: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, showAnimationOnly: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false)` |

### Properties

| Name | Summary |
|---|---|
| [drawableBitmap](drawable-bitmap.md) | `val drawableBitmap: `[`Bitmap`](https://developer.android.com/reference/android/graphics/Bitmap.html)`?` |
| [id](id.md) | `val id: `[`UUID`](https://developer.android.com/reference/java/util/UUID.html) |
| [isShowOnConversationLayer](is-show-on-conversation-layer.md) | `val isShowOnConversationLayer: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [language](language.md) | `val language: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [packageName](package-name.md) | `var packageName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [showAnimationOnly](show-animation-only.md) | `val showAnimationOnly: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [speech](speech.md) | `val speech: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [status](status.md) | `var status: `[`TtsRequest.Status`](-status/index.md) |

### Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | `fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [equals](equals.md) | `fun equals(other: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | `fun hashCode(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | `fun writeToParcel(dest: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Properties

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r.md) | `val CREATOR: `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`TtsRequest`](./index.md)`>` |

### Companion Object Functions

| Name | Summary |
|---|---|
| [create](create.md) | `fun create(speech: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, isShowOnConversationLayer: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true, language: `[`TtsRequest.Language`](-language/index.md)` = Language.SYSTEM, showAnimationOnly: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false): `[`TtsRequest`](./index.md) |
