[sdk](../../index.md) / [com.robotemi.sdk.face](../index.md) / [ContactModel](./index.md)

# ContactModel

`data class ContactModel : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ContactModel(source: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`)`<br>`ContactModel(firstName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "", lastName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "", gender: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "", imageKey: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "", description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "", userId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)` = "")` |

### Properties

| Name | Summary |
|---|---|
| [description](description.md) | `val description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [firstName](first-name.md) | `val firstName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [gender](gender.md) | `val gender: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [imageKey](image-key.md) | `val imageKey: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [lastName](last-name.md) | `val lastName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [userId](user-id.md) | `val userId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | `fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | `fun writeToParcel(dest: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Properties

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r.md) | `val CREATOR: `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`ContactModel`](./index.md)`>` |
| [JSON_KEY_DESCRIPTION](-j-s-o-n_-k-e-y_-d-e-s-c-r-i-p-t-i-o-n.md) | `const val JSON_KEY_DESCRIPTION: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [JSON_KEY_USER_ID](-j-s-o-n_-k-e-y_-u-s-e-r_-i-d.md) | `const val JSON_KEY_USER_ID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
