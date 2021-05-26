[sdk](../../index.md) / [com.robotemi.sdk.model](../index.md) / [CallEventModel](./index.md)

# CallEventModel

`data class CallEventModel : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

### Annotations

| Name | Summary |
|---|---|
| [CallState](-call-state/index.md) | `annotation class CallState` |
| [CallType](-call-type/index.md) | `annotation class CallType` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CallEventModel(source: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`)`<br>`CallEventModel(sessionId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, type: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, state: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`)` |

### Properties

| Name | Summary |
|---|---|
| [sessionId](session-id.md) | `var sessionId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [state](state.md) | `var state: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [type](type.md) | `var type: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | `fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | `fun writeToParcel(dest: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Properties

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r.md) | `val CREATOR: `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`CallEventModel`](./index.md)`>` |
| [STATE_ENDED](-s-t-a-t-e_-e-n-d-e-d.md) | `const val STATE_ENDED: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [STATE_STARTED](-s-t-a-t-e_-s-t-a-r-t-e-d.md) | `const val STATE_STARTED: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [TYPE_INCOMING](-t-y-p-e_-i-n-c-o-m-i-n-g.md) | `const val TYPE_INCOMING: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [TYPE_OUTGOING](-t-y-p-e_-o-u-t-g-o-i-n-g.md) | `const val TYPE_OUTGOING: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
