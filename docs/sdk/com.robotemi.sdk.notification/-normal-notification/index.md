[sdk](../../index.md) / [com.robotemi.sdk.notification](../index.md) / [NormalNotification](./index.md)

# NormalNotification

`open class NormalNotification : `[`Notification`](../-notification/index.md)`, `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

### Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | `open class Builder` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `NormalNotification(in: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`!)` |

### Properties

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r.md) | `static val CREATOR: `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`NormalNotification`](./index.md)`!>!` |

### Functions

| Name | Summary |
|---|---|
| [builder](builder.md) | `open static fun builder(title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!): `[`NormalNotification.Builder`](-builder/index.md)`!` |
| [describeContents](describe-contents.md) | `open fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getBackgroundColor](get-background-color.md) | `open fun getBackgroundColor(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getBitmap](get-bitmap.md) | `open fun getBitmap(): `[`Bitmap`](https://developer.android.com/reference/android/graphics/Bitmap.html)`?` |
| [getIconResource](get-icon-resource.md) | `open fun getIconResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getNotificationId](get-notification-id.md) | `open fun getNotificationId(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getPrimaryButton](get-primary-button.md) | `open fun getPrimaryButton(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getPrimaryPendingIntent](get-primary-pending-intent.md) | `open fun getPrimaryPendingIntent(): `[`PendingIntent`](https://developer.android.com/reference/android/app/PendingIntent.html)`!` |
| [getSecondaryButton](get-secondary-button.md) | `open fun getSecondaryButton(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getSecondaryPendingIntent](get-secondary-pending-intent.md) | `open fun getSecondaryPendingIntent(): `[`PendingIntent`](https://developer.android.com/reference/android/app/PendingIntent.html)`!` |
| [getSubtitle](get-subtitle.md) | `open fun getSubtitle(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getSubtitleResource](get-subtitle-resource.md) | `open fun getSubtitleResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getTextColor](get-text-color.md) | `open fun getTextColor(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getTimeout](get-timeout.md) | `open fun getTimeout(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getTitle](get-title.md) | `open fun getTitle(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getTitleResource](get-title-resource.md) | `open fun getTitleResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getType](get-type.md) | `open fun getType(): `[`Notification.Type`](../-notification/-type/index.md)`!` |
| [writeToParcel](write-to-parcel.md) | `open fun writeToParcel(dest: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`!, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
