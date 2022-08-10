//[sdk](../../../index.md)/[com.robotemi.sdk.map](../index.md)/[Location](index.md)

# Location

[androidJvm]\
data class [Location](index.md)(val x: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), val y: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), val yaw: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val tiltAngle: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f) : Parcelable

## Constructors

| | |
|---|---|
| [Location](-location.md) | [androidJvm]<br>fun [Location](-location.md)(parcel: Parcel) |
| [Location](-location.md) | [androidJvm]<br>fun [Location](-location.md)(x: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), y: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), yaw: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tiltAngle: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f) |

## Types

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r/index.md) | [androidJvm]<br>object [CREATOR](-c-r-e-a-t-o-r/index.md) : Parcelable.Creator&lt;[Location](index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | [androidJvm]<br>open override fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open override fun [writeToParcel](write-to-parcel.md)(parcel: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [name](name.md) | [androidJvm]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [tiltAngle](tilt-angle.md) | [androidJvm]<br>@SerializedName(value = &quot;tilt_angle&quot;)<br>val [tiltAngle](tilt-angle.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f |
| [x](x.md) | [androidJvm]<br>val [x](x.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [y](y.md) | [androidJvm]<br>val [y](y.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [yaw](yaw.md) | [androidJvm]<br>val [yaw](yaw.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
