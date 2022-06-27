//[sdk](../../../index.md)/[com.robotemi.sdk](../index.md)/[Robot](index.md)/[goTo](go-to.md)

# goTo

[androidJvm]\

@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)

fun [goTo](go-to.md)(location: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), backwards: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)? = null, noBypass: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)? = null, speedLevel: [SpeedLevel](../../com.robotemi.sdk.navigation.model/-speed-level/index.md)? = null)

Send robot to previously saved location.

## Parameters

androidJvm

| | |
|---|---|
| location | Saved location name. |
| backwards | if `true` will walk backwards to the destination. `false` by default. |
| noBypass | if `true` will disallow bypass the obstacles during go to. Pass `null` to follow the  *Settings -*> *Navigation Settings* |
| speedLevel | the speed level of this single go to session. Pass `null` to start with the speed level in *Settings -*> *Navigation Settings* (see [speedLevel](go-to.md)). |
