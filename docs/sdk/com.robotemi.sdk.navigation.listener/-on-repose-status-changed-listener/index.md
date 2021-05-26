[sdk](../../index.md) / [com.robotemi.sdk.navigation.listener](../index.md) / [OnReposeStatusChangedListener](./index.md)

# OnReposeStatusChangedListener

`interface OnReposeStatusChangedListener`

### Annotations

| Name | Summary |
|---|---|
| [Status](-status/index.md) | `annotation class Status` |

### Functions

| Name | Summary |
|---|---|
| [onReposeStatusChanged](on-repose-status-changed.md) | `abstract fun onReposeStatusChanged(status: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Properties

| Name | Summary |
|---|---|
| [IDLE](-i-d-l-e.md) | `const val IDLE: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [REPOSE_REQUIRED](-r-e-p-o-s-e_-r-e-q-u-i-r-e-d.md) | `const val REPOSE_REQUIRED: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [REPOSING_ABORT](-r-e-p-o-s-i-n-g_-a-b-o-r-t.md) | `const val REPOSING_ABORT: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [REPOSING_COMPLETE](-r-e-p-o-s-i-n-g_-c-o-m-p-l-e-t-e.md) | `const val REPOSING_COMPLETE: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [REPOSING_GOING](-r-e-p-o-s-i-n-g_-g-o-i-n-g.md) | `const val REPOSING_GOING: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [REPOSING_OBSTACLE_DETECTED](-r-e-p-o-s-i-n-g_-o-b-s-t-a-c-l-e_-d-e-t-e-c-t-e-d.md) | `const val REPOSING_OBSTACLE_DETECTED: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [REPOSING_START](-r-e-p-o-s-i-n-g_-s-t-a-r-t.md) | `const val REPOSING_START: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
