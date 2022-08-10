//[sdk](../../../index.md)/[com.robotemi.sdk.map](../index.md)/[MapImage](index.md)

# MapImage

[androidJvm]\
data class [MapImage](index.md)(val typeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val rows: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val cols: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val dt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val data: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;) : Parcelable

## Constructors

| | |
|---|---|
| [MapImage](-map-image.md) | [androidJvm]<br>fun [MapImage](-map-image.md)(source: Parcel) |
| [MapImage](-map-image.md) | [androidJvm]<br>fun [MapImage](-map-image.md)(typeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), rows: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), cols: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), dt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), data: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;) |

## Types

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r/index.md) | [androidJvm]<br>object [CREATOR](-c-r-e-a-t-o-r/index.md) : Parcelable.Creator&lt;[MapImage](index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | [androidJvm]<br>open override fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open override fun [writeToParcel](write-to-parcel.md)(dest: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [cols](cols.md) | [androidJvm]<br>val [cols](cols.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [data](data.md) | [androidJvm]<br>val [data](data.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt; |
| [dt](dt.md) | [androidJvm]<br>val [dt](dt.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [rows](rows.md) | [androidJvm]<br>val [rows](rows.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [typeId](type-id.md) | [androidJvm]<br>@SerializedName(value = &quot;type_id&quot;)<br>val [typeId](type-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
