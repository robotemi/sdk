[sdk](../../index.md) / [com.robotemi.sdk.listeners](../index.md) / [OnGoToLocationStatusChangedListener](./index.md)

# OnGoToLocationStatusChangedListener

`interface OnGoToLocationStatusChangedListener`

### Annotations

| Name | Summary |
|---|---|
| [GoToLocationStatus](-go-to-location-status/index.md) | `annotation class GoToLocationStatus` |

### Functions

| Name | Summary |
|---|---|
| [onGoToLocationStatusChanged](on-go-to-location-status-changed.md) | `abstract fun onGoToLocationStatusChanged(location: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, status: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, descriptionId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Listen for status changes during 'go to location'. |

### Companion Object Properties

| Name | Summary |
|---|---|
| [ABORT](-a-b-o-r-t.md) | `const val ABORT: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [CALCULATING](-c-a-l-c-u-l-a-t-i-n-g.md) | `const val CALCULATING: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [COMPLETE](-c-o-m-p-l-e-t-e.md) | `const val COMPLETE: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [GOING](-g-o-i-n-g.md) | `const val GOING: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [REPOSING](-r-e-p-o-s-i-n-g.md) | `const val REPOSING: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [START](-s-t-a-r-t.md) | `const val START: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
