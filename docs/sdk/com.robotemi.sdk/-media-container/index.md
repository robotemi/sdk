[sdk](../../index.md) / [com.robotemi.sdk](../index.md) / [MediaContainer](./index.md)

# MediaContainer

`interface MediaContainer`

### Functions

| Name | Summary |
|---|---|
| [getLocalPath](get-local-path.md) | `abstract fun getLocalPath(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getMediaUri](get-media-uri.md) | `abstract fun getMediaUri(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getMimeType](get-mime-type.md) | `abstract fun getMimeType(): `[`MediaObject.MimeType`](../-media-object/-mime-type/index.md)`!` |
| [setMediaUri](set-media-uri.md) | `abstract fun setMediaUri(uri: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inheritors

| Name | Summary |
|---|---|
| [ActivityStreamListItem](../../com.robotemi.sdk.activitystream/-activity-stream-list-item/index.md) | `open class ActivityStreamListItem : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)`, `[`MediaContainer`](./index.md) |
| [MediaObject](../-media-object/index.md) | `open class MediaObject : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)`, `[`MediaContainer`](./index.md) |
