[sdk](../../index.md) / [com.robotemi.sdk.map](../index.md) / [LayerPose](./index.md)

# LayerPose

`data class LayerPose : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

### Types

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r/index.md) | `companion object CREATOR : `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`LayerPose`](./index.md)`>` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `LayerPose(parcel: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`)`<br>`LayerPose(x: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`, y: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`, theta: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`)` |

### Properties

| Name | Summary |
|---|---|
| [theta](theta.md) | `val theta: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [x](x.md) | `val x: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [y](y.md) | `val y: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |

### Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | `fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | `fun writeToParcel(parcel: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [createFromParcel](create-from-parcel.md) | `fun createFromParcel(parcel: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`): `[`LayerPose`](./index.md) |
| [newArray](new-array.md) | `fun newArray(size: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`LayerPose`](./index.md)`?>` |
