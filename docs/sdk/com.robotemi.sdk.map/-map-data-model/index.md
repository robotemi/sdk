[sdk](../../index.md) / [com.robotemi.sdk.map](../index.md) / [MapDataModel](./index.md)

# MapDataModel

`data class MapDataModel : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

### Types

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r/index.md) | `companion object CREATOR : `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`MapDataModel`](./index.md)`>` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `MapDataModel(parcel: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`)`<br>`MapDataModel(mapImage: `[`MapImage`](../-map-image/index.md)`, mapId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "", mapInfo: `[`MapInfo`](../-map-info/index.md)` = MapInfo(), virtualWalls: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Layer`](../-layer/index.md)`> = mutableListOf(), greenPaths: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Layer`](../-layer/index.md)`> = mutableListOf(), locations: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Layer`](../-layer/index.md)`> = mutableListOf())` |

### Properties

| Name | Summary |
|---|---|
| [greenPaths](green-paths.md) | `var greenPaths: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Layer`](../-layer/index.md)`>` |
| [locations](locations.md) | `var locations: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Layer`](../-layer/index.md)`>` |
| [mapId](map-id.md) | `var mapId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [mapImage](map-image.md) | `var mapImage: `[`MapImage`](../-map-image/index.md) |
| [mapInfo](map-info.md) | `var mapInfo: `[`MapInfo`](../-map-info/index.md) |
| [virtualWalls](virtual-walls.md) | `var virtualWalls: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Layer`](../-layer/index.md)`>` |

### Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | `fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | `fun writeToParcel(parcel: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [createFromParcel](create-from-parcel.md) | `fun createFromParcel(parcel: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`): `[`MapDataModel`](./index.md) |
| [newArray](new-array.md) | `fun newArray(size: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`MapDataModel`](./index.md)`?>` |
