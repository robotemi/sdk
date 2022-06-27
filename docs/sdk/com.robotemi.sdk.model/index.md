//[sdk](../../index.md)/[com.robotemi.sdk.model](index.md)

# Package com.robotemi.sdk.model

## Types

| Name | Summary |
|---|---|
| [CallEventModel](-call-event-model/index.md) | [androidJvm]<br>data class [CallEventModel](-call-event-model/index.md)(var sessionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), var type: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), var state: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : Parcelable |
| [DetectionData](-detection-data/index.md) | [androidJvm]<br>data class [DetectionData](-detection-data/index.md)(val angle: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), val distance: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), val isDetected: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) : Parcelable |
| [MemberStatusModel](-member-status-model/index.md) | [androidJvm]<br>data class [MemberStatusModel](-member-status-model/index.md)(val memberId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val mobileStatus: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = STATUS_OFFLINE, val centerStatus: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = STATUS_OFFLINE) : Parcelable |
| [RecentCallModel](-recent-call-model/index.md) | [androidJvm]<br>data class [RecentCallModel](-recent-call-model/index.md)(val userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val timestamp: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, val sessionId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val callType: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : Parcelable |
