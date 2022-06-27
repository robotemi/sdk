//[sdk](../../../index.md)/[com.robotemi.sdk.activitystream](../index.md)/[ActivityStreamObject](index.md)

# ActivityStreamObject

[androidJvm]\
open class [ActivityStreamObject](index.md) : Parcelable

## Types

| Name | Summary |
|---|---|
| [ActivityType](-activity-type/index.md) | [androidJvm]<br>enum [ActivityType](-activity-type/index.md) |
| [Builder](-builder/index.md) | [androidJvm]<br>open class [Builder](-builder/index.md) |

## Functions

| Name | Summary |
|---|---|
| [builder](builder.md) | [androidJvm]<br>open fun [builder](builder.md)(): [ActivityStreamObject.Builder](-builder/index.md) |
| [describeContents](describe-contents.md) | [androidJvm]<br>open fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open fun [writeToParcel](write-to-parcel.md)(dest: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [activityType](activity-type.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;type&quot;)<br>open val [activityType](activity-type.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411) | [androidJvm]<br>val [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [CREATOR](-c-r-e-a-t-o-r.md) | [androidJvm]<br>val [CREATOR](-c-r-e-a-t-o-r.md): Parcelable.Creator&lt;[ActivityStreamObject](index.md)&gt; |
| [date](date.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;date&quot;)<br>open val [date](date.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [items](items.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;items&quot;)<br>open val [items](items.md): [List](https://docs.oracle.com/javase/8/docs/api/java/util/List.html)&lt;[ActivityStreamListItem](../-activity-stream-list-item/index.md)&gt; |
| [mediaObject](media-object.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;media&quot;)<br>open val [mediaObject](media-object.md): [MediaObject](../../com.robotemi.sdk/-media-object/index.md) |
| [message](message.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;message&quot;)<br>open val [message](message.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [numOfProvidedFiles](num-of-provided-files.md) | [androidJvm]<br>open val [numOfProvidedFiles](num-of-provided-files.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411) | [androidJvm]<br>val [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [sourceObject](source-object.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;source&quot;)<br>open val [sourceObject](source-object.md): [SourceObject](../../com.robotemi.sdk/-source-object/index.md) |
| [title](title.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;title&quot;)<br>open val [title](title.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [uuid](uuid.md) | [androidJvm]<br>open val [uuid](uuid.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
