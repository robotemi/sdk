[sdk](../../index.md) / [com.robotemi.sdk.mediabar](../index.md) / [MediaBarController](./index.md)

# MediaBarController

`interface MediaBarController`

### Functions

| Name | Summary |
|---|---|
| [pauseMediaBar](pause-media-bar.md) | `abstract fun pauseMediaBar(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>pauses the media |
| [setMediaPlaying](set-media-playing.md) | `abstract fun setMediaPlaying(isPlaying: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, packageName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`!): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Indicate that media is being played |
| [updateMediaBar](update-media-bar.md) | `abstract fun updateMediaBar(mediaBarData: `[`MediaBarData`](../-media-bar-data/index.md)`!): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>set media . |

### Inheritors

| Name | Summary |
|---|---|
| [AidlMediaBarController](../-aidl-media-bar-controller/index.md) | `open class AidlMediaBarController : `[`MediaBarController`](./index.md) |
