//[sdk](../../../../index.md)/[com.robotemi.sdk.exception](../../index.md)/[SdkException](../index.md)/[Companion](index.md)

# Companion

[androidJvm]\
object [Companion](index.md)

## Functions

| Name | Summary |
|---|---|
| [illegalArgument](illegal-argument.md) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [illegalArgument](illegal-argument.md)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [SdkException](../index.md) |
| [launcherError](launcher-error.md) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [launcherError](launcher-error.md)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [SdkException](../index.md) |
| [operationConflict](operation-conflict.md) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [operationConflict](operation-conflict.md)(msg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [SdkException](../index.md) |
| [permissionDenied](permission-denied.md) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [permissionDenied](permission-denied.md)(permission: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [SdkException](../index.md) |

## Properties

| Name | Summary |
|---|---|
| [CODE_ILLEGAL_ARGUMENT](-c-o-d-e_-i-l-l-e-g-a-l_-a-r-g-u-m-e-n-t.md) | [androidJvm]<br>const val [CODE_ILLEGAL_ARGUMENT](-c-o-d-e_-i-l-l-e-g-a-l_-a-r-g-u-m-e-n-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 400 |
| [CODE_LAUNCHER_ERROR](-c-o-d-e_-l-a-u-n-c-h-e-r_-e-r-r-o-r.md) | [androidJvm]<br>const val [CODE_LAUNCHER_ERROR](-c-o-d-e_-l-a-u-n-c-h-e-r_-e-r-r-o-r.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 500 |
| [CODE_OPERATION_CONFLICT](-c-o-d-e_-o-p-e-r-a-t-i-o-n_-c-o-n-f-l-i-c-t.md) | [androidJvm]<br>const val [CODE_OPERATION_CONFLICT](-c-o-d-e_-o-p-e-r-a-t-i-o-n_-c-o-n-f-l-i-c-t.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 409 |
| [CODE_PERMISSION_DENIED](-c-o-d-e_-p-e-r-m-i-s-s-i-o-n_-d-e-n-i-e-d.md) | [androidJvm]<br>const val [CODE_PERMISSION_DENIED](-c-o-d-e_-p-e-r-m-i-s-s-i-o-n_-d-e-n-i-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 403 |
| [CREATOR](-c-r-e-a-t-o-r.md) | [androidJvm]<br>@[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-field/index.html)<br>val [CREATOR](-c-r-e-a-t-o-r.md): Parcelable.Creator&lt;[SdkException](../index.md)&gt; |
