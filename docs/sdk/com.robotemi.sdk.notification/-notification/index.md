[sdk](../../index.md) / [com.robotemi.sdk.notification](../index.md) / [Notification](./index.md)

# Notification

`interface Notification`

### Types

| Name | Summary |
|---|---|
| [Type](-type/index.md) | `class Type` |

### Functions

| Name | Summary |
|---|---|
| [getBitmap](get-bitmap.md) | `abstract fun getBitmap(): `[`Bitmap`](https://developer.android.com/reference/android/graphics/Bitmap.html)`?` |
| [getIconResource](get-icon-resource.md) | `abstract fun getIconResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getNotificationId](get-notification-id.md) | `abstract fun getNotificationId(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getSubtitle](get-subtitle.md) | `abstract fun getSubtitle(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getSubtitleResource](get-subtitle-resource.md) | `abstract fun getSubtitleResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getTitle](get-title.md) | `abstract fun getTitle(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getTitleResource](get-title-resource.md) | `abstract fun getTitleResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getType](get-type.md) | `abstract fun getType(): `[`Notification.Type`](-type/index.md)`!` |

### Inheritors

| Name | Summary |
|---|---|
| [AlertNotification](../-alert-notification/index.md) | `open class AlertNotification : `[`Notification`](./index.md)`, `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html) |
| [BillboardNotification](../-billboard-notification/index.md) | `open class BillboardNotification : `[`Notification`](./index.md) |
| [NormalNotification](../-normal-notification/index.md) | `open class NormalNotification : `[`Notification`](./index.md)`, `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html) |
