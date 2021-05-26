[sdk](../../index.md) / [com.robotemi.sdk.mediabar](../index.md) / [MediaBarData](./index.md)

# MediaBarData

`open class MediaBarData : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

### Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | `open class Builder` |
| [Type](-type/index.md) | `class Type` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `MediaBarData(in: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`!)` |

### Properties

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r.md) | `static val CREATOR: `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`MediaBarData`](./index.md)`!>!` |

### Functions

| Name | Summary |
|---|---|
| [builder](builder.md) | `open static fun builder(title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!, type: `[`MediaBarData.Type`](-type/index.md)`!): `[`MediaBarData.Builder`](-builder/index.md)`!` |
| [describeContents](describe-contents.md) | `open fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getBitmap](get-bitmap.md) | `open fun getBitmap(): `[`Bitmap`](https://developer.android.com/reference/android/graphics/Bitmap.html)`!` |
| [getCurrentTrackPosition](get-current-track-position.md) | `open fun getCurrentTrackPosition(): `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [getIconResource](get-icon-resource.md) | `open fun getIconResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getImageUrl](get-image-url.md) | `open fun getImageUrl(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getPackageName](get-package-name.md) | `open fun getPackageName(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getSubtitle](get-subtitle.md) | `open fun getSubtitle(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getSubtitleResource](get-subtitle-resource.md) | `open fun getSubtitleResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getTitle](get-title.md) | `open fun getTitle(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getTitleResource](get-title-resource.md) | `open fun getTitleResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getTrackDuration](get-track-duration.md) | `open fun getTrackDuration(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getType](get-type.md) | `open fun getType(): `[`MediaBarData.Type`](-type/index.md)`!` |
| [isPlaying](is-playing.md) | `open fun isPlaying(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [setPackageName](set-package-name.md) | `open fun setPackageName(packageName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [toString](to-string.md) | `open fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [writeToParcel](write-to-parcel.md) | `open fun writeToParcel(dest: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`!, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
