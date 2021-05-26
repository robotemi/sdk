[sdk](../../index.md) / [com.robotemi.sdk](../index.md) / [MediaObject](./index.md)

# MediaObject

`open class MediaObject : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)`, `[`MediaContainer`](../-media-container/index.md)

### Types

| Name | Summary |
|---|---|
| [MimeType](-mime-type/index.md) | `class MimeType` |

### Properties

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r.md) | `static val CREATOR: `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`MediaObject`](./index.md)`!>!` |

### Functions

| Name | Summary |
|---|---|
| [create](create.md) | `open static fun create(mimeType: `[`MediaObject.MimeType`](-mime-type/index.md)`!, file: `[`File`](https://developer.android.com/reference/java/io/File.html)`!): `[`MediaObject`](./index.md)`!` |
| [describeContents](describe-contents.md) | `open fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getFile](get-file.md) | `open fun getFile(): `[`File`](https://developer.android.com/reference/java/io/File.html)`!` |
| [getLocalPath](get-local-path.md) | `open fun getLocalPath(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getMediaUri](get-media-uri.md) | `open fun getMediaUri(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getMimeType](get-mime-type.md) | `open fun getMimeType(): `[`MediaObject.MimeType`](-mime-type/index.md)`!` |
| [setMediaUri](set-media-uri.md) | `open fun setMediaUri(uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [writeToParcel](write-to-parcel.md) | `open fun writeToParcel(dest: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`!, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
