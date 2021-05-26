[sdk](../../index.md) / [com.robotemi.sdk](../index.md) / [UserInfo](./index.md)

# UserInfo

`data class UserInfo : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

### Types

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r/index.md) | `companion object CREATOR : `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`UserInfo`](./index.md)`>` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `UserInfo(parcel: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`)`<br>`UserInfo(userId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, picUrl: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`? = "", role: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`)` |

### Properties

| Name | Summary |
|---|---|
| [name](name.md) | `val name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [picUrl](pic-url.md) | `val picUrl: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [role](role.md) | `val role: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [userId](user-id.md) | `val userId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | `fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | `fun writeToParcel(parcel: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [createFromParcel](create-from-parcel.md) | `fun createFromParcel(parcel: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`): `[`UserInfo`](./index.md) |
| [newArray](new-array.md) | `fun newArray(size: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<`[`UserInfo`](./index.md)`?>` |
