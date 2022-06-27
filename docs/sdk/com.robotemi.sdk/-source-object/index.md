//[sdk](../../../index.md)/[com.robotemi.sdk](../index.md)/[SourceObject](index.md)

# SourceObject

[androidJvm]\
open class [SourceObject](index.md) : Parcelable

## Functions

| Name | Summary |
|---|---|
| [create](create.md) | [androidJvm]<br>open fun [create](create.md)(name: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), iconUri: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)): [SourceObject](index.md) |
| [describeContents](describe-contents.md) | [androidJvm]<br>open fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open fun [writeToParcel](write-to-parcel.md)(dest: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411) | [androidJvm]<br>val [CONTENTS_FILE_DESCRIPTOR](../../com.robotemi.sdk.telepresence/-call-state/index.md#-255210448%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [CREATOR](-c-r-e-a-t-o-r.md) | [androidJvm]<br>val [CREATOR](-c-r-e-a-t-o-r.md): Parcelable.Creator&lt;[SourceObject](index.md)&gt; |
| [iconUri](icon-uri.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;iconUri&quot;)<br>open val [iconUri](icon-uri.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [name](name.md) | [androidJvm]<br>@Expose<br>@SerializedName(value = &quot;name&quot;)<br>open val [name](name.md): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411) | [androidJvm]<br>val [PARCELABLE_WRITE_RETURN_VALUE](../../com.robotemi.sdk.telepresence/-call-state/index.md#-865205281%2FProperties%2F462465411): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
