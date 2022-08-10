//[sdk](../../../index.md)/[com.robotemi.sdk](../index.md)/[Robot](index.md)/[goToPosition](go-to-position.md)

# goToPosition

[androidJvm]\

@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)

fun [goToPosition](go-to-position.md)(position: [Position](../../com.robotemi.sdk.navigation.model/-position/index.md), backwards: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)? = null, noBypass: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)? = null, speedLevel: [SpeedLevel](../../com.robotemi.sdk.navigation.model/-speed-level/index.md)? = null)

Go to a specific position with (x,y).

## Parameters

androidJvm

| | |
|---|---|
| position | Position holds (x,y). |
| backwards | if `true` will walk backwards to the destination. `false` by default. |
| noBypass | if `true` will disallow bypass the obstacles during go to. Pass `null` to follow the  *Settings -*> *Navigation Settings* |
| speedLevel | the speed level of this single go to session. Pass `null` to start with the speed level in *Settings -*> *Navigation Settings* (see [speedLevel](go-to-position.md)). |
