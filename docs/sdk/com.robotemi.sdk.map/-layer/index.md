//[sdk](../../../index.md)/[com.robotemi.sdk.map](../index.md)/[Layer](index.md)

# Layer

[androidJvm]\
data class [Layer](index.md)(val layerCreationUTC: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val layerCategory: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val layerId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val layerThickness: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), val layerStatus: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val layerPoses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LayerPose](../-layer-pose/index.md)&gt;?) : Parcelable

## Constructors

| | |
|---|---|
| [Layer](-layer.md) | [androidJvm]<br>fun [Layer](-layer.md)(parcel: Parcel) |
| [Layer](-layer.md) | [androidJvm]<br>fun [Layer](-layer.md)(layerCreationUTC: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), layerCategory: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), layerId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, layerThickness: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), layerStatus: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), layerPoses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LayerPose](../-layer-pose/index.md)&gt;?) |

## Types

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r/index.md) | [androidJvm]<br>object [CREATOR](-c-r-e-a-t-o-r/index.md) : Parcelable.Creator&lt;[Layer](index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | [androidJvm]<br>open override fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toString](to-string.md) | [androidJvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open override fun [writeToParcel](write-to-parcel.md)(parcel: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [layerCategory](layer-category.md) | [androidJvm]<br>@SerializedName(value = &quot;layer_category&quot;)<br>val [layerCategory](layer-category.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [layerCreationUTC](layer-creation-u-t-c.md) | [androidJvm]<br>@SerializedName(value = &quot;layer_creation_universal_time&quot;)<br>val [layerCreationUTC](layer-creation-u-t-c.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [layerId](layer-id.md) | [androidJvm]<br>@SerializedName(value = &quot;layer_id&quot;)<br>val [layerId](layer-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [layerPoses](layer-poses.md) | [androidJvm]<br>@SerializedName(value = &quot;layer_poses&quot;)<br>val [layerPoses](layer-poses.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LayerPose](../-layer-pose/index.md)&gt;? |
| [layerStatus](layer-status.md) | [androidJvm]<br>@SerializedName(value = &quot;layer_status&quot;)<br>val [layerStatus](layer-status.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [layerThickness](layer-thickness.md) | [androidJvm]<br>@SerializedName(value = &quot;layer_thickness&quot;)<br>val [layerThickness](layer-thickness.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
