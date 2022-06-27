//[sdk](../../../index.md)/[com.robotemi.sdk.model](../index.md)/[MemberStatusModel](index.md)

# MemberStatusModel

[androidJvm]\
data class [MemberStatusModel](index.md)(val memberId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val mobileStatus: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = STATUS_OFFLINE, val centerStatus: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = STATUS_OFFLINE) : Parcelable

## Constructors

| | |
|---|---|
| [MemberStatusModel](-member-status-model.md) | [androidJvm]<br>fun [MemberStatusModel](-member-status-model.md)(parcel: Parcel) |
| [MemberStatusModel](-member-status-model.md) | [androidJvm]<br>fun [MemberStatusModel](-member-status-model.md)(memberId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), mobileStatus: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = STATUS_OFFLINE, centerStatus: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = STATUS_OFFLINE) |

## Types

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r/index.md) | [androidJvm]<br>object [CREATOR](-c-r-e-a-t-o-r/index.md) : Parcelable.Creator&lt;[MemberStatusModel](index.md)&gt; |
| [Status](-status/index.md) | [androidJvm]<br>annotation class [Status](-status/index.md) |

## Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | [androidJvm]<br>open override fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open override fun [writeToParcel](write-to-parcel.md)(parcel: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [centerStatus](center-status.md) | [androidJvm]<br>val [centerStatus](center-status.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [memberId](member-id.md) | [androidJvm]<br>val [memberId](member-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [mobileStatus](mobile-status.md) | [androidJvm]<br>val [mobileStatus](mobile-status.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
