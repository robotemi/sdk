//[sdk](../../../index.md)/[com.robotemi.sdk.map](../index.md)/[LayerPose](index.md)

# LayerPose

[androidJvm]\
data class [LayerPose](index.md)(val x: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), val y: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), val theta: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)) : Parcelable

## Constructors

| | |
|---|---|
| [LayerPose](-layer-pose.md) | [androidJvm]<br>fun [LayerPose](-layer-pose.md)(parcel: Parcel) |
| [LayerPose](-layer-pose.md) | [androidJvm]<br>fun [LayerPose](-layer-pose.md)(x: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), y: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), theta: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)) |

## Types

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r/index.md) | [androidJvm]<br>object [CREATOR](-c-r-e-a-t-o-r/index.md) : Parcelable.Creator&lt;[LayerPose](index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | [androidJvm]<br>open override fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open override fun [writeToParcel](write-to-parcel.md)(parcel: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [theta](theta.md) | [androidJvm]<br>@SerializedName(value = &quot;theta&quot;)<br>val [theta](theta.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [x](x.md) | [androidJvm]<br>@SerializedName(value = &quot;x&quot;)<br>val [x](x.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [y](y.md) | [androidJvm]<br>@SerializedName(value = &quot;y&quot;)<br>val [y](y.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
