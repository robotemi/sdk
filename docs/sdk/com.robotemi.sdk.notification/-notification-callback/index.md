//[sdk](../../../index.md)/[com.robotemi.sdk.notification](../index.md)/[NotificationCallback](index.md)

# NotificationCallback

[androidJvm]\
open class [NotificationCallback](index.md) : Parcelable

a class representing a notification id and the button number that was pressed used to return information to outside skills that requested showing a notification with button events

## Constructors

| | |
|---|---|
| [NotificationCallback](-notification-callback.md) | [androidJvm]<br>open fun [NotificationCallback](-notification-callback.md)(@NullablenotificationId: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), event: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | [androidJvm]<br>open fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [empty](empty.md) | [androidJvm]<br>open fun [empty](empty.md)(): [NotificationCallback](index.md) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open fun [writeToParcel](write-to-parcel.md)(dest: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411) | [androidJvm]<br>val [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [CREATOR](-c-r-e-a-t-o-r.md) | [androidJvm]<br>val [CREATOR](-c-r-e-a-t-o-r.md): Parcelable.Creator&lt;[NotificationCallback](index.md)&gt; |
| [event](event.md) | [androidJvm]<br>open val [event](event.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [INVALID_EVENT](-i-n-v-a-l-i-d_-e-v-e-n-t.md) | [androidJvm]<br>val [INVALID_EVENT](-i-n-v-a-l-i-d_-e-v-e-n-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [notificationId](notification-id.md) | [androidJvm]<br>@Nullable<br>@get:Nullable<br>open val [notificationId](notification-id.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411) | [androidJvm]<br>val [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [PRIMARY_BUTTON_EVENT](-p-r-i-m-a-r-y_-b-u-t-t-o-n_-e-v-e-n-t.md) | [androidJvm]<br>val [PRIMARY_BUTTON_EVENT](-p-r-i-m-a-r-y_-b-u-t-t-o-n_-e-v-e-n-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [SECONDARY_BUTTON_EVENT](-s-e-c-o-n-d-a-r-y_-b-u-t-t-o-n_-e-v-e-n-t.md) | [androidJvm]<br>val [SECONDARY_BUTTON_EVENT](-s-e-c-o-n-d-a-r-y_-b-u-t-t-o-n_-e-v-e-n-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [TIMEOUT_EVENT](-t-i-m-e-o-u-t_-e-v-e-n-t.md) | [androidJvm]<br>val [TIMEOUT_EVENT](-t-i-m-e-o-u-t_-e-v-e-n-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
