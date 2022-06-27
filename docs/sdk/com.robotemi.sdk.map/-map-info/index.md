//[sdk](../../../index.md)/[com.robotemi.sdk.map](../index.md)/[MapInfo](index.md)

# MapInfo

[androidJvm]\
data class [MapInfo](index.md)(val height: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val width: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val originX: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f, val originY: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f, val resolution: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f) : Parcelable

## Constructors

| | |
|---|---|
| [MapInfo](-map-info.md) | [androidJvm]<br>fun [MapInfo](-map-info.md)(parcel: Parcel) |
| [MapInfo](-map-info.md) | [androidJvm]<br>fun [MapInfo](-map-info.md)(height: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, width: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, originX: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f, originY: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f, resolution: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f) |

## Types

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r/index.md) | [androidJvm]<br>object [CREATOR](-c-r-e-a-t-o-r/index.md) : Parcelable.Creator&lt;[MapInfo](index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | [androidJvm]<br>open override fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open override fun [writeToParcel](write-to-parcel.md)(parcel: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [height](height.md) | [androidJvm]<br>@SerializedName(value = &quot;height&quot;)<br>val [height](height.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0 |
| [originX](origin-x.md) | [androidJvm]<br>@SerializedName(value = &quot;origin_x&quot;)<br>val [originX](origin-x.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f |
| [originY](origin-y.md) | [androidJvm]<br>@SerializedName(value = &quot;origin_y&quot;)<br>val [originY](origin-y.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f |
| [resolution](resolution.md) | [androidJvm]<br>@SerializedName(value = &quot;resolution&quot;)<br>val [resolution](resolution.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f |
| [width](width.md) | [androidJvm]<br>@SerializedName(value = &quot;width&quot;)<br>val [width](width.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0 |
