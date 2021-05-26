[sdk](../../index.md) / [com.robotemi.sdk.listeners](../index.md) / [OnConversationStatusChangedListener](./index.md)

# OnConversationStatusChangedListener

`interface OnConversationStatusChangedListener`

### Annotations

| Name | Summary |
|---|---|
| [Status](-status/index.md) | `annotation class Status` |

### Functions

| Name | Summary |
|---|---|
| [onConversationStatusChanged](on-conversation-status-changed.md) | `abstract fun onConversationStatusChanged(status: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, text: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Properties

| Name | Summary |
|---|---|
| [IDLE](-i-d-l-e.md) | `const val IDLE: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [LISTENING](-l-i-s-t-e-n-i-n-g.md) | `const val LISTENING: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [SPEAKING](-s-p-e-a-k-i-n-g.md) | `const val SPEAKING: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [THINKING](-t-h-i-n-k-i-n-g.md) | `const val THINKING: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
