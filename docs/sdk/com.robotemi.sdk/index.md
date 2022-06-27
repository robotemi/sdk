//[sdk](../../index.md)/[com.robotemi.sdk](index.md)

# Package com.robotemi.sdk

## Types

| Name | Summary |
|---|---|
| [BatteryData](-battery-data/index.md) | [androidJvm]<br>data class [BatteryData](-battery-data/index.md)(val level: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val isCharging: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) : Parcelable |
| [MediaContainer](-media-container/index.md) | [androidJvm]<br>interface [MediaContainer](-media-container/index.md) |
| [MediaObject](-media-object/index.md) | [androidJvm]<br>open class [MediaObject](-media-object/index.md) : Parcelable, [MediaContainer](-media-container/index.md) |
| [NlpResult](-nlp-result/index.md) | [androidJvm]<br>class [NlpResult](-nlp-result/index.md) : Parcelable |
| [Robot](-robot/index.md) | [androidJvm]<br>class [Robot](-robot/index.md) |
| [SourceObject](-source-object/index.md) | [androidJvm]<br>open class [SourceObject](-source-object/index.md) : Parcelable |
| [TtsRequest](-tts-request/index.md) | [androidJvm]<br>data class [TtsRequest](-tts-request/index.md)(val id: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html) = UUID.randomUUID(), val speech: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), var packageName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var status: [TtsRequest.Status](-tts-request/-status/index.md) = Status.PENDING, val drawableBitmap: Bitmap? = null, val isShowOnConversationLayer: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val language: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val showAnimationOnly: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : Parcelable |
| [UserInfo](-user-info/index.md) | [androidJvm]<br>data class [UserInfo](-user-info/index.md)(val userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val picUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;&quot;, val role: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : Parcelable |
