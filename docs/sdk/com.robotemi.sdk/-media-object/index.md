//[sdk](../../../index.md)/[com.robotemi.sdk](../index.md)/[MediaObject](index.md)

# MediaObject

[androidJvm]\
open class [MediaObject](index.md) : Parcelable, [MediaContainer](../-media-container/index.md)

## Types

| Name | Summary |
|---|---|
| [MimeType](-mime-type/index.md) | [androidJvm]<br>enum [MimeType](-mime-type/index.md) |

## Functions

| Name | Summary |
|---|---|
| [create](create.md) | [androidJvm]<br>open fun [create](create.md)(mimeType: [MediaObject.MimeType](-mime-type/index.md), file: [File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html)): [MediaObject](index.md) |
| [describeContents](describe-contents.md) | [androidJvm]<br>open fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getMediaUri](get-media-uri.md) | [androidJvm]<br>open fun [getMediaUri](get-media-uri.md)(): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [setMediaUri](set-media-uri.md) | [androidJvm]<br>open fun [setMediaUri](set-media-uri.md)(uri: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open fun [writeToParcel](write-to-parcel.md)(dest: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411) | [androidJvm]<br>val [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [CREATOR](-c-r-e-a-t-o-r.md) | [androidJvm]<br>val [CREATOR](-c-r-e-a-t-o-r.md): Parcelable.Creator&lt;[MediaObject](index.md)&gt; |
| [file](file.md) | [androidJvm]<br>open val [file](file.md): [File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html) |
| [localPath](local-path.md) | [androidJvm]<br>@get:NonNull<br>open val [localPath](local-path.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [mimeType](mime-type.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;mimetype&quot;)<br>open val [mimeType](mime-type.md): [MediaObject.MimeType](-mime-type/index.md) |
| [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411) | [androidJvm]<br>val [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
