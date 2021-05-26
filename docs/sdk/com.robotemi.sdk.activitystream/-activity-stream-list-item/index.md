[sdk](../../index.md) / [com.robotemi.sdk.activitystream](../index.md) / [ActivityStreamListItem](./index.md)

# ActivityStreamListItem

`open class ActivityStreamListItem : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)`, `[`MediaContainer`](../../com.robotemi.sdk/-media-container/index.md)

### Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | `open class Builder` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ActivityStreamListItem(title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, file: `[`File`](https://developer.android.com/reference/java/io/File.html)`, url: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?, date: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?, mimeType: `[`MediaObject.MimeType`](../../com.robotemi.sdk/-media-object/-mime-type/index.md)`?)` |

### Properties

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r.md) | `static val CREATOR: `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`ActivityStreamListItem`](./index.md)`!>!` |

### Functions

| Name | Summary |
|---|---|
| [builder](builder.md) | `open static fun builder(): `[`ActivityStreamListItem.Builder`](-builder/index.md)`!` |
| [describeContents](describe-contents.md) | `open fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getDate](get-date.md) | `open fun getDate(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [getFile](get-file.md) | `open fun getFile(): `[`File`](https://developer.android.com/reference/java/io/File.html)`?` |
| [getLocalPath](get-local-path.md) | `open fun getLocalPath(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getMediaUri](get-media-uri.md) | `open fun getMediaUri(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [getMessage](get-message.md) | `open fun getMessage(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getMimeType](get-mime-type.md) | `open fun getMimeType(): `[`MediaObject.MimeType`](../../com.robotemi.sdk/-media-object/-mime-type/index.md)`!` |
| [getTitle](get-title.md) | `open fun getTitle(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getUrl](get-url.md) | `open fun getUrl(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [isFileProvided](is-file-provided.md) | `open fun isFileProvided(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [setMediaUri](set-media-uri.md) | `open fun setMediaUri(uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeToParcel](write-to-parcel.md) | `open fun writeToParcel(dest: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`!, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
