[sdk](../../index.md) / [com.robotemi.sdk](../index.md) / [NlpResult](./index.md)

# NlpResult

`class NlpResult : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `NlpResult(in: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`!)`<br>`NlpResult()` |

### Properties

| Name | Summary |
|---|---|
| [action](action.md) | `var action: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [CREATOR](-c-r-e-a-t-o-r.md) | `static val CREATOR: `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`NlpResult`](./index.md)`!>!` |
| [params](params.md) | `var params: `[`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!, `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!>!` |
| [resolvedQuery](resolved-query.md) | `var resolvedQuery: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |

### Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | `fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [writeToParcel](write-to-parcel.md) | `fun writeToParcel(dest: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`!, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
