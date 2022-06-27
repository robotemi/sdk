//[sdk](../../../index.md)/[com.robotemi.sdk.notification](../index.md)/[NormalNotification](index.md)

# NormalNotification

[androidJvm]\
open class [NormalNotification](index.md) : [Notification](../-notification/index.md), Parcelable

## Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | [androidJvm]<br>open class [Builder](-builder/index.md) |

## Functions

| Name | Summary |
|---|---|
| [builder](builder.md) | [androidJvm]<br>open fun [builder](builder.md)(title: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)): [NormalNotification.Builder](-builder/index.md) |
| [describeContents](describe-contents.md) | [androidJvm]<br>open fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open fun [writeToParcel](write-to-parcel.md)(dest: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [backgroundColor](background-color.md) | [androidJvm]<br>open val [backgroundColor](background-color.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [bitmap](bitmap.md) | [androidJvm]<br>@get:Nullable<br>open val [bitmap](bitmap.md): Bitmap |
| [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411) | [androidJvm]<br>val [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [CREATOR](-c-r-e-a-t-o-r.md) | [androidJvm]<br>val [CREATOR](-c-r-e-a-t-o-r.md): Parcelable.Creator&lt;[NormalNotification](index.md)&gt; |
| [iconResource](icon-resource.md) | [androidJvm]<br>@DrawableRes<br>open val [iconResource](icon-resource.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [notificationId](notification-id.md) | [androidJvm]<br>open val [notificationId](notification-id.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411) | [androidJvm]<br>val [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [primaryButton](primary-button.md) | [androidJvm]<br>open val [primaryButton](primary-button.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [primaryPendingIntent](primary-pending-intent.md) | [androidJvm]<br>open val [primaryPendingIntent](primary-pending-intent.md): PendingIntent |
| [secondaryButton](secondary-button.md) | [androidJvm]<br>open val [secondaryButton](secondary-button.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [secondaryPendingIntent](secondary-pending-intent.md) | [androidJvm]<br>open val [secondaryPendingIntent](secondary-pending-intent.md): PendingIntent |
| [subtitle](subtitle.md) | [androidJvm]<br>open val [subtitle](subtitle.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [subtitleResource](subtitle-resource.md) | [androidJvm]<br>@StringRes<br>open val [subtitleResource](subtitle-resource.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [textColor](text-color.md) | [androidJvm]<br>open val [textColor](text-color.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [timeout](timeout.md) | [androidJvm]<br>open val [timeout](timeout.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [title](title.md) | [androidJvm]<br>open val [title](title.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [titleResource](title-resource.md) | [androidJvm]<br>@StringRes<br>open val [titleResource](title-resource.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [type](type.md) | [androidJvm]<br>open val [type](type.md): [Notification.Type](../-notification/-type/index.md) |
