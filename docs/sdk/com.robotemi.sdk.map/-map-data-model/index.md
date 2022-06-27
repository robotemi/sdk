//[sdk](../../../index.md)/[com.robotemi.sdk.map](../index.md)/[MapDataModel](index.md)

# MapDataModel

[androidJvm]\
data class [MapDataModel](index.md)(var mapImage: [MapImage](../-map-image/index.md), var mapId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var mapInfo: [MapInfo](../-map-info/index.md) = MapInfo(), var virtualWalls: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Layer](../-layer/index.md)&gt; = mutableListOf(), var greenPaths: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Layer](../-layer/index.md)&gt; = mutableListOf(), var locations: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Layer](../-layer/index.md)&gt; = mutableListOf()) : Parcelable

## Constructors

| | |
|---|---|
| [MapDataModel](-map-data-model.md) | [androidJvm]<br>fun [MapDataModel](-map-data-model.md)(parcel: Parcel) |
| [MapDataModel](-map-data-model.md) | [androidJvm]<br>fun [MapDataModel](-map-data-model.md)(mapImage: [MapImage](../-map-image/index.md), mapId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, mapInfo: [MapInfo](../-map-info/index.md) = MapInfo(), virtualWalls: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Layer](../-layer/index.md)&gt; = mutableListOf(), greenPaths: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Layer](../-layer/index.md)&gt; = mutableListOf(), locations: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Layer](../-layer/index.md)&gt; = mutableListOf()) |

## Types

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r/index.md) | [androidJvm]<br>object [CREATOR](-c-r-e-a-t-o-r/index.md) : Parcelable.Creator&lt;[MapDataModel](index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | [androidJvm]<br>open override fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open override fun [writeToParcel](write-to-parcel.md)(parcel: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [greenPaths](green-paths.md) | [androidJvm]<br>var [greenPaths](green-paths.md): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Layer](../-layer/index.md)&gt; |
| [locations](locations.md) | [androidJvm]<br>var [locations](locations.md): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Layer](../-layer/index.md)&gt; |
| [mapId](map-id.md) | [androidJvm]<br>var [mapId](map-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [mapImage](map-image.md) | [androidJvm]<br>@SerializedName(value = &quot;Map_Image&quot;)<br>var [mapImage](map-image.md): [MapImage](../-map-image/index.md) |
| [mapInfo](map-info.md) | [androidJvm]<br>var [mapInfo](map-info.md): [MapInfo](../-map-info/index.md) |
| [virtualWalls](virtual-walls.md) | [androidJvm]<br>var [virtualWalls](virtual-walls.md): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Layer](../-layer/index.md)&gt; |
