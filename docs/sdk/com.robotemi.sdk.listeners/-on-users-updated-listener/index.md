[sdk](../../index.md) / [com.robotemi.sdk.listeners](../index.md) / [OnUsersUpdatedListener](./index.md)

# OnUsersUpdatedListener

`abstract class OnUsersUpdatedListener`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `OnUsersUpdatedListener(userIds: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>?)` |

### Properties

| Name | Summary |
|---|---|
| [userIds](user-ids.md) | `var userIds: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>?` |

### Functions

| Name | Summary |
|---|---|
| [onUserUpdated](on-user-updated.md) | `abstract fun onUserUpdated(user: `[`UserInfo`](../../com.robotemi.sdk/-user-info/index.md)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Called when users info was changed. |
