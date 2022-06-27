//[sdk](../../../index.md)/[com.robotemi.sdk.notification](../index.md)/[Notification](index.md)

# Notification

[androidJvm]\
interface [Notification](index.md)

## Types

| Name | Summary |
|---|---|
| [Type](-type/index.md) | [androidJvm]<br>enum [Type](-type/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getBitmap](get-bitmap.md) | [androidJvm]<br>@Nullable<br>abstract fun [getBitmap](get-bitmap.md)(): Bitmap |
| [getIconResource](get-icon-resource.md) | [androidJvm]<br>@DrawableRes<br>abstract fun [getIconResource](get-icon-resource.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getNotificationId](get-notification-id.md) | [androidJvm]<br>abstract fun [getNotificationId](get-notification-id.md)(): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [getSubtitle](get-subtitle.md) | [androidJvm]<br>abstract fun [getSubtitle](get-subtitle.md)(): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [getSubtitleResource](get-subtitle-resource.md) | [androidJvm]<br>@StringRes<br>abstract fun [getSubtitleResource](get-subtitle-resource.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getTitle](get-title.md) | [androidJvm]<br>abstract fun [getTitle](get-title.md)(): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [getTitleResource](get-title-resource.md) | [androidJvm]<br>abstract fun [getTitleResource](get-title-resource.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getType](get-type.md) | [androidJvm]<br>abstract fun [getType](get-type.md)(): [Notification.Type](-type/index.md) |

## Inheritors

| Name |
|---|
| [AlertNotification](../-alert-notification/index.md) |
| [BillboardNotification](../-billboard-notification/index.md) |
| [NormalNotification](../-normal-notification/index.md) |
