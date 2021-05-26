[sdk](../../index.md) / [com.robotemi.sdk](../index.md) / [Robot](index.md) / [lockContexts](./lock-contexts.md)

# lockContexts

`fun ~~lockContexts~~(contextsToLock: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)
**Deprecated:** No longer supported

Request to lock contexts even if skill screen is dismissed.
Useful for services running in the background without UI.

### Parameters

`contextsToLock` -
* List of contexts names to lock.
