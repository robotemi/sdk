[sdk](../../index.md) / [com.robotemi.sdk.notification](../index.md) / [AlertNotification](./index.md)

# AlertNotification

`open class AlertNotification : `[`Notification`](../-notification/index.md)`, `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

### Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | `open class Builder` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AlertNotification(in: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`!)` |

### Properties

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r.md) | `static val CREATOR: `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`AlertNotification`](./index.md)`!>!` |

### Functions

| Name | Summary |
|---|---|
| [builder](builder.md) | `open static fun builder(title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!): `[`AlertNotification.Builder`](-builder/index.md)`!` |
| [describeContents](describe-contents.md) | `open fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getAltTextRes](get-alt-text-res.md) | `open fun getAltTextRes(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getBitmap](get-bitmap.md) | `open fun getBitmap(): `[`Bitmap`](https://developer.android.com/reference/android/graphics/Bitmap.html)`?` |
| [getCheckBoxText](get-check-box-text.md) | `open fun getCheckBoxText(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getIconResource](get-icon-resource.md) | `open fun getIconResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getNotificationId](get-notification-id.md) | `open fun getNotificationId(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getPrimaryBtnText](get-primary-btn-text.md) | `open fun getPrimaryBtnText(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getPrimaryBtnTextResource](get-primary-btn-text-resource.md) | `open fun getPrimaryBtnTextResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getQuite](get-quite.md) | `open fun getQuite(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getSecondaryBtnText](get-secondary-btn-text.md) | `open fun getSecondaryBtnText(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getSecondaryBtnTextResource](get-secondary-btn-text-resource.md) | `open fun getSecondaryBtnTextResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getSubtitle](get-subtitle.md) | `open fun getSubtitle(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getSubtitleResource](get-subtitle-resource.md) | `open fun getSubtitleResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getTimeout](get-timeout.md) | `open fun getTimeout(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getTitle](get-title.md) | `open fun getTitle(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getTitleResource](get-title-resource.md) | `open fun getTitleResource(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getType](get-type.md) | `open fun getType(): `[`Notification.Type`](../-notification/-type/index.md)`!` |
| [isAutoTimeout](is-auto-timeout.md) | `open fun isAutoTimeout(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isDismissible](is-dismissible.md) | `open fun isDismissible(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [writeToParcel](write-to-parcel.md) | `open fun writeToParcel(dest: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`!, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
