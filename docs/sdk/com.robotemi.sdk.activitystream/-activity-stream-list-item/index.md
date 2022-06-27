//[sdk](../../../index.md)/[com.robotemi.sdk.activitystream](../index.md)/[ActivityStreamListItem](index.md)

# ActivityStreamListItem

[androidJvm]\
open class [ActivityStreamListItem](index.md) : Parcelable, [MediaContainer](../../com.robotemi.sdk/-media-container/index.md)

## Constructors

| | |
|---|---|
| [ActivityStreamListItem](-activity-stream-list-item.md) | [androidJvm]<br>open fun [ActivityStreamListItem](-activity-stream-list-item.md)(@NonNulltitle: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), @NonNullmessage: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), @NonNullfile: [File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html), @Nullableurl: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), @Nullabledate: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), @NullablemimeType: [MediaObject.MimeType](../../com.robotemi.sdk/-media-object/-mime-type/index.md)) |

## Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | [androidJvm]<br>open class [Builder](-builder/index.md) |

## Functions

| Name | Summary |
|---|---|
| [builder](builder.md) | [androidJvm]<br>open fun [builder](builder.md)(): [ActivityStreamListItem.Builder](-builder/index.md) |
| [describeContents](describe-contents.md) | [androidJvm]<br>open fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open fun [writeToParcel](write-to-parcel.md)(dest: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411) | [androidJvm]<br>val [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [CREATOR](-c-r-e-a-t-o-r.md) | [androidJvm]<br>val [CREATOR](-c-r-e-a-t-o-r.md): Parcelable.Creator&lt;[ActivityStreamListItem](index.md)&gt; |
| [date](date.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;date&quot;)<br>@get:Nullable<br>open val [date](date.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [file](file.md) | [androidJvm]<br>@get:Nullable<br>open val [file](file.md): [File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html) |
| [isFileProvided](is-file-provided.md) | [androidJvm]<br>open val [isFileProvided](is-file-provided.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [localPath](local-path.md) | [androidJvm]<br>open val [localPath](local-path.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [mediaUri](media-uri.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;mediaUrl&quot;)<br>@get:Nullable<br>open var [mediaUri](media-uri.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [message](message.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;message&quot;)<br>@get:NonNull<br>open val [message](message.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [mimeType](mime-type.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;mimetype&quot;)<br>open val [mimeType](mime-type.md): [MediaObject.MimeType](../../com.robotemi.sdk/-media-object/-mime-type/index.md) |
| [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411) | [androidJvm]<br>val [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [title](title.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;title&quot;)<br>@get:NonNull<br>open val [title](title.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [url](url.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;url&quot;)<br>@get:Nullable<br>open val [url](url.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
