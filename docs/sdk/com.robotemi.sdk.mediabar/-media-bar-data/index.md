//[sdk](../../../index.md)/[com.robotemi.sdk.mediabar](../index.md)/[MediaBarData](index.md)

# MediaBarData

[androidJvm]\
open class [MediaBarData](index.md) : Parcelable

## Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | [androidJvm]<br>open class [Builder](-builder/index.md) |
| [Type](-type/index.md) | [androidJvm]<br>enum [Type](-type/index.md) |

## Functions

| Name | Summary |
|---|---|
| [builder](builder.md) | [androidJvm]<br>open fun [builder](builder.md)(title: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), type: [MediaBarData.Type](-type/index.md)): [MediaBarData.Builder](-builder/index.md) |
| [describeContents](describe-contents.md) | [androidJvm]<br>open fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getCurrentTrackPosition](get-current-track-position.md) | [androidJvm]<br>open fun [getCurrentTrackPosition](get-current-track-position.md)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [toString](to-string.md) | [androidJvm]<br>open fun [toString](to-string.md)(): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open fun [writeToParcel](write-to-parcel.md)(dest: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [bitmap](bitmap.md) | [androidJvm]<br>val [bitmap](bitmap.md): Bitmap |
| [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411) | [androidJvm]<br>val [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [CREATOR](-c-r-e-a-t-o-r.md) | [androidJvm]<br>val [CREATOR](-c-r-e-a-t-o-r.md): Parcelable.Creator&lt;[MediaBarData](index.md)&gt; |
| [iconResource](icon-resource.md) | [androidJvm]<br>@StringRes<br>val [iconResource](icon-resource.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [imageUrl](image-url.md) | [androidJvm]<br>open val [imageUrl](image-url.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [isPlaying](is-playing.md) | [androidJvm]<br>open val [isPlaying](is-playing.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [packageName](package-name.md) | [androidJvm]<br>open var [packageName](package-name.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411) | [androidJvm]<br>val [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [subtitle](subtitle.md) | [androidJvm]<br>open val [subtitle](subtitle.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [subtitleResource](subtitle-resource.md) | [androidJvm]<br>@StringRes<br>val [subtitleResource](subtitle-resource.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [title](title.md) | [androidJvm]<br>open val [title](title.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [titleResource](title-resource.md) | [androidJvm]<br>@StringRes<br>val [titleResource](title-resource.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [trackDuration](track-duration.md) | [androidJvm]<br>open val [trackDuration](track-duration.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [type](type.md) | [androidJvm]<br>val [type](type.md): [MediaBarData.Type](-type/index.md) |
