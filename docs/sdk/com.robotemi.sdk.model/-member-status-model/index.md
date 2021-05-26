[sdk](../../index.md) / [com.robotemi.sdk.model](../index.md) / [MemberStatusModel](./index.md)

# MemberStatusModel

`data class MemberStatusModel : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

### Types

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r/index.md) | `companion object CREATOR : `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`MemberStatusModel`](./index.md)`>` |

### Annotations

| Name | Summary |
|---|---|
| [Status](-status/index.md) | `annotation class Status` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `MemberStatusModel(parcel: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`)`<br>`MemberStatusModel(memberId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, mobileStatus: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = STATUS_OFFLINE, centerStatus: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = STATUS_OFFLINE)` |

### Properties

| Name | Summary |
|---|---|
| [centerStatus](center-status.md) | `val centerStatus: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [memberId](member-id.md) | `val memberId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [mobileStatus](mobile-status.md) | `val mobileStatus: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | `fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | `fun writeToParcel(parcel: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Properties

| Name | Summary |
|---|---|
| [STATUS_BUSY](-s-t-a-t-u-s_-b-u-s-y.md) | `const val STATUS_BUSY: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [STATUS_OFFLINE](-s-t-a-t-u-s_-o-f-f-l-i-n-e.md) | `const val STATUS_OFFLINE: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [STATUS_ONLINE](-s-t-a-t-u-s_-o-n-l-i-n-e.md) | `const val STATUS_ONLINE: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [createFromParcel](create-from-parcel.md) | `fun createFromParcel(parcel: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`): `[`MemberStatusModel`](./index.md) |
| [newArray](new-array.md) | `fun newArray(size: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`MemberStatusModel`](./index.md)`?>` |
