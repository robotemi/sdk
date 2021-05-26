[sdk](../../index.md) / [com.robotemi.sdk.permission](../index.md) / [Permission](./index.md)

# Permission

`enum class Permission`

All permissions can be requested for now.

### Annotations

| Name | Summary |
|---|---|
| [PermissionResult](-permission-result/index.md) | `annotation class PermissionResult` |

### Enum Values

| Name | Summary |
|---|---|
| [FACE_RECOGNITION](-f-a-c-e_-r-e-c-o-g-n-i-t-i-o-n.md) |  |
| [MAP](-m-a-p.md) |  |
| [SETTINGS](-s-e-t-t-i-n-g-s.md) |  |
| [SEQUENCE](-s-e-q-u-e-n-c-e.md) |  |
| [UNKNOWN](-u-n-k-n-o-w-n.md) |  |

### Properties

| Name | Summary |
|---|---|
| [isKioskPermission](is-kiosk-permission.md) | `val isKioskPermission: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Deprecated. |
| [value](value.md) | `val value: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The String type value of the Permission. |

### Companion Object Properties

| Name | Summary |
|---|---|
| [DENIED](-d-e-n-i-e-d.md) | `const val DENIED: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Permission check result: this is returned by [com.robotemi.sdk.Robot.checkSelfPermission](../../com.robotemi.sdk/-robot/check-self-permission.md) if the permission has not been granted to the given package. |
| [GRANTED](-g-r-a-n-t-e-d.md) | `const val GRANTED: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Permission check result: this is returned by [com.robotemi.sdk.Robot.checkSelfPermission](../../com.robotemi.sdk/-robot/check-self-permission.md) if the permission has been granted to the given package. |

### Companion Object Functions

| Name | Summary |
|---|---|
| [isKioskPermission](is-kiosk-permission.md) | `fun isKioskPermission(permission: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isValidPermission](is-valid-permission.md) | `fun isValidPermission(permission: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [valueToEnum](value-to-enum.md) | `fun valueToEnum(value: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Permission`](./index.md) |
