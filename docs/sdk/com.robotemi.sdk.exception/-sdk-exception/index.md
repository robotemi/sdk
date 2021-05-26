[sdk](../../index.md) / [com.robotemi.sdk.exception](../index.md) / [SdkException](./index.md)

# SdkException

`data class SdkException : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SdkException(source: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`)`<br>`SdkException(code: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`)` |

### Properties

| Name | Summary |
|---|---|
| [code](code.md) | `var code: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [message](message.md) | `var message: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | `fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | `fun writeToParcel(dest: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Properties

| Name | Summary |
|---|---|
| [CODE_ILLEGAL_ARGUMENT](-c-o-d-e_-i-l-l-e-g-a-l_-a-r-g-u-m-e-n-t.md) | `const val CODE_ILLEGAL_ARGUMENT: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [CODE_LAUNCHER_ERROR](-c-o-d-e_-l-a-u-n-c-h-e-r_-e-r-r-o-r.md) | `const val CODE_LAUNCHER_ERROR: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [CODE_OPERATION_CONFLICT](-c-o-d-e_-o-p-e-r-a-t-i-o-n_-c-o-n-f-l-i-c-t.md) | `const val CODE_OPERATION_CONFLICT: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [CODE_PERMISSION_DENIED](-c-o-d-e_-p-e-r-m-i-s-s-i-o-n_-d-e-n-i-e-d.md) | `const val CODE_PERMISSION_DENIED: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [CREATOR](-c-r-e-a-t-o-r.md) | `val CREATOR: `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`SdkException`](./index.md)`>` |

### Companion Object Functions

| Name | Summary |
|---|---|
| [illegalArgument](illegal-argument.md) | `fun illegalArgument(msg: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`SdkException`](./index.md) |
| [launcherError](launcher-error.md) | `fun launcherError(msg: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`SdkException`](./index.md) |
| [operationConflict](operation-conflict.md) | `fun operationConflict(msg: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`SdkException`](./index.md) |
| [permissionDenied](permission-denied.md) | `fun permissionDenied(permission: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`SdkException`](./index.md) |
