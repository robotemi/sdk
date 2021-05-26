[sdk](../../index.md) / [com.robotemi.sdk.activitystream](../index.md) / [ActivityStreamObject](./index.md)

# ActivityStreamObject

`open class ActivityStreamObject : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)

### Types

| Name | Summary |
|---|---|
| [ActivityType](-activity-type/index.md) | `class ActivityType` |
| [Builder](-builder/index.md) | `open class Builder` |

### Properties

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r.md) | `static val CREATOR: `[`Creator`](https://developer.android.com/reference/android/os/Parcelable/Creator.html)`<`[`ActivityStreamObject`](./index.md)`!>!` |

### Functions

| Name | Summary |
|---|---|
| [builder](builder.md) | `open static fun builder(): `[`ActivityStreamObject.Builder`](-builder/index.md)`!` |
| [describeContents](describe-contents.md) | `open fun describeContents(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getActivityType](get-activity-type.md) | `open fun getActivityType(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getDate](get-date.md) | `open fun getDate(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getItems](get-items.md) | `open fun getItems(): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`ActivityStreamListItem`](../-activity-stream-list-item/index.md)`!>!` |
| [getMediaObject](get-media-object.md) | `open fun getMediaObject(): `[`MediaObject`](../../com.robotemi.sdk/-media-object/index.md)`!` |
| [getMessage](get-message.md) | `open fun getMessage(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getNumOfProvidedFiles](get-num-of-provided-files.md) | `open fun getNumOfProvidedFiles(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getSourceObject](get-source-object.md) | `open fun getSourceObject(): `[`SourceObject`](../../com.robotemi.sdk/-source-object/index.md)`!` |
| [getTitle](get-title.md) | `open fun getTitle(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [getUuid](get-uuid.md) | `open fun getUuid(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!` |
| [writeToParcel](write-to-parcel.md) | `open fun writeToParcel(dest: `[`Parcel`](https://developer.android.com/reference/android/os/Parcel.html)`!, flags: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
