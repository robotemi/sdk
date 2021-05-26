[sdk](../../index.md) / [com.robotemi.sdk.notification](../index.md) / [NotificationCallback](./index.md)

# NotificationCallback

`open class NotificationCallback : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

a class representing a notification id and the button number that was pressed used to return information to outside skills that requested showing a notification with button events

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `NotificationCallback(notificationId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?, event: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`)`<br>`NotificationCallback(in: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`!)` |

### Properties

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r.md) | `static val CREATOR: `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`NotificationCallback`](./index.md)`!>!` |
| [INVALID_EVENT](-i-n-v-a-l-i-d_-e-v-e-n-t.md) | `static val INVALID_EVENT: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [PRIMARY_BUTTON_EVENT](-p-r-i-m-a-r-y_-b-u-t-t-o-n_-e-v-e-n-t.md) | `static val PRIMARY_BUTTON_EVENT: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [SECONDARY_BUTTON_EVENT](-s-e-c-o-n-d-a-r-y_-b-u-t-t-o-n_-e-v-e-n-t.md) | `static val SECONDARY_BUTTON_EVENT: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [TIMEOUT_EVENT](-t-i-m-e-o-u-t_-e-v-e-n-t.md) | `static val TIMEOUT_EVENT: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | `open fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [empty](empty.md) | `open static fun empty(): `[`NotificationCallback`](./index.md)`!` |
| [getEvent](get-event.md) | `open fun getEvent(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getNotificationId](get-notification-id.md) | `open fun getNotificationId(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [writeToParcel](write-to-parcel.md) | `open fun writeToParcel(dest: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`!, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
