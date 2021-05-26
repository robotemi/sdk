[sdk](../../index.md) / [com.robotemi.sdk.listeners](../index.md) / [OnMovementStatusChangedListener](./index.md)

# OnMovementStatusChangedListener

`interface OnMovementStatusChangedListener`

### Annotations

| Name | Summary |
|---|---|
| [Status](-status/index.md) | `annotation class Status` |
| [Type](-type/index.md) | `annotation class Type` |

### Functions

| Name | Summary |
|---|---|
| [onMovementStatusChanged](on-movement-status-changed.md) | `abstract fun onMovementStatusChanged(type: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, status: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Properties

| Name | Summary |
|---|---|
| [STATUS_ABORT](-s-t-a-t-u-s_-a-b-o-r-t.md) | `const val STATUS_ABORT: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [STATUS_CALCULATING](-s-t-a-t-u-s_-c-a-l-c-u-l-a-t-i-n-g.md) | `const val STATUS_CALCULATING: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [STATUS_COMPLETE](-s-t-a-t-u-s_-c-o-m-p-l-e-t-e.md) | `const val STATUS_COMPLETE: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [STATUS_GOING](-s-t-a-t-u-s_-g-o-i-n-g.md) | `const val STATUS_GOING: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [STATUS_NODE_INACTIVE](-s-t-a-t-u-s_-n-o-d-e_-i-n-a-c-t-i-v-e.md) | `const val STATUS_NODE_INACTIVE: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [STATUS_OBSTACLE_DETECTED](-s-t-a-t-u-s_-o-b-s-t-a-c-l-e_-d-e-t-e-c-t-e-d.md) | `const val STATUS_OBSTACLE_DETECTED: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [STATUS_START](-s-t-a-t-u-s_-s-t-a-r-t.md) | `const val STATUS_START: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [TYPE_SKID_JOY](-t-y-p-e_-s-k-i-d_-j-o-y.md) | `const val TYPE_SKID_JOY: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [TYPE_TURN_BY](-t-y-p-e_-t-u-r-n_-b-y.md) | `const val TYPE_TURN_BY: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
