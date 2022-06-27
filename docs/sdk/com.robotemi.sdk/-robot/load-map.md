//[sdk](../../../index.md)/[com.robotemi.sdk](../index.md)/[Robot](index.md)/[loadMap](load-map.md)

# loadMap

[androidJvm]\

@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)

fun [loadMap](load-map.md)(mapId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), reposeRequired: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, position: [Position](../../com.robotemi.sdk.navigation.model/-position/index.md)? = null, offline: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, withoutUI: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Load map by map ID. The result is broadcast from onLoadMapStatusChanged

#### Return

Request id. In the format of UUID, e.g. 538b44c9-fdcf-426a-9693-d72e9c0f9550. Used in onLoadMapStatusChanged callback.

## Parameters

androidJvm

| | |
|---|---|
| mapId | The map ID of the map to be loaded |
| reposeRequired | If needs to repose after loading map, default as false |
| position | The position for repose |
| offline | Skip fetching the latest map data of target mapId, default as false. |
| withoutUI | Load the map in the background without showing any blocking UI, default as false. |
