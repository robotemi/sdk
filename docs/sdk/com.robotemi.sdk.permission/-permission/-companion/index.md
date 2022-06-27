//[sdk](../../../../index.md)/[com.robotemi.sdk.permission](../../index.md)/[Permission](../index.md)/[Companion](index.md)

# Companion

[androidJvm]\
object [Companion](index.md)

## Functions

| Name | Summary |
|---|---|
| [isKioskPermission](is-kiosk-permission.md) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [isKioskPermission](is-kiosk-permission.md)(permission: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isValidPermission](is-valid-permission.md) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [isValidPermission](is-valid-permission.md)(permission: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [valueToEnum](value-to-enum.md) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [valueToEnum](value-to-enum.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Permission](../index.md) |

## Properties

| Name | Summary |
|---|---|
| [DENIED](-d-e-n-i-e-d.md) | [androidJvm]<br>const val [DENIED](-d-e-n-i-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0<br>Permission check result: this is returned by [com.robotemi.sdk.Robot.checkSelfPermission](../../../com.robotemi.sdk/-robot/check-self-permission.md) if the permission has not been granted to the given package. |
| [GRANTED](-g-r-a-n-t-e-d.md) | [androidJvm]<br>const val [GRANTED](-g-r-a-n-t-e-d.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 1<br>Permission check result: this is returned by [com.robotemi.sdk.Robot.checkSelfPermission](../../../com.robotemi.sdk/-robot/check-self-permission.md) if the permission has been granted to the given package. |
