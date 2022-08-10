//[sdk](../../../index.md)/[com.robotemi.sdk](../index.md)/[UserInfo](index.md)

# UserInfo

[androidJvm]\
data class [UserInfo](index.md)(val userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val picUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;&quot;, val role: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : Parcelable

## Constructors

| | |
|---|---|
| [UserInfo](-user-info.md) | [androidJvm]<br>fun [UserInfo](-user-info.md)(parcel: Parcel) |
| [UserInfo](-user-info.md) | [androidJvm]<br>fun [UserInfo](-user-info.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), picUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;&quot;, role: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Types

| Name | Summary |
|---|---|
| [CREATOR](-c-r-e-a-t-o-r/index.md) | [androidJvm]<br>object [CREATOR](-c-r-e-a-t-o-r/index.md) : Parcelable.Creator&lt;[UserInfo](index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [describeContents](describe-contents.md) | [androidJvm]<br>open override fun [describeContents](describe-contents.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](write-to-parcel.md) | [androidJvm]<br>open override fun [writeToParcel](write-to-parcel.md)(parcel: Parcel, flags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [name](name.md) | [androidJvm]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [picUrl](pic-url.md) | [androidJvm]<br>val [picUrl](pic-url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [role](role.md) | [androidJvm]<br>val [role](role.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [userId](user-id.md) | [androidJvm]<br>val [userId](user-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
