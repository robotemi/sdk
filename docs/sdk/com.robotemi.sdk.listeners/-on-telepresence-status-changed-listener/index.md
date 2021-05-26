[sdk](../../index.md) / [com.robotemi.sdk.listeners](../index.md) / [OnTelepresenceStatusChangedListener](./index.md)

# OnTelepresenceStatusChangedListener

`abstract class OnTelepresenceStatusChangedListener`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `OnTelepresenceStatusChangedListener(sessionId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`)` |

### Properties

| Name | Summary |
|---|---|
| [sessionId](session-id.md) | `var sessionId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| Name | Summary |
|---|---|
| [onTelepresenceStatusChanged](on-telepresence-status-changed.md) | `abstract fun onTelepresenceStatusChanged(callState: `[`CallState`](../../com.robotemi.sdk.telepresence/-call-state/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Called when Telepresence status was changed. |
