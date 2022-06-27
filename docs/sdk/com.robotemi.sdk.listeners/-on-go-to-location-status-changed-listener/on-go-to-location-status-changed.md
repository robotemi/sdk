//[sdk](../../../index.md)/[com.robotemi.sdk.listeners](../index.md)/[OnGoToLocationStatusChangedListener](index.md)/[onGoToLocationStatusChanged](on-go-to-location-status-changed.md)

# onGoToLocationStatusChanged

[androidJvm]\
abstract fun [onGoToLocationStatusChanged](on-go-to-location-status-changed.md)(location: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), status: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), descriptionId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Listen for status changes during 'go to location'.

Available statuses:

- 
   [OnGoToLocationStatusChangedListener.START](-companion/-s-t-a-r-t.md)
- 
   [OnGoToLocationStatusChangedListener.CALCULATING](-companion/-c-a-l-c-u-l-a-t-i-n-g.md)
- 
   [OnGoToLocationStatusChangedListener.GOING](-companion/-g-o-i-n-g.md)
- 
   [OnGoToLocationStatusChangedListener.COMPLETE](-companion/-c-o-m-p-l-e-t-e.md)
- 
   [OnGoToLocationStatusChangedListener.ABORT](-companion/-a-b-o-r-t.md)
- 
   [OnGoToLocationStatusChangedListener.REPOSING](-companion/-r-e-p-o-s-i-n-g.md)

## Parameters

androidJvm

| | |
|---|---|
| location | Location of GoTo response. |
| status | Current status. |
