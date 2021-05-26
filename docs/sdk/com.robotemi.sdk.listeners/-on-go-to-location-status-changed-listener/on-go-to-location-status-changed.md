[sdk](../../index.md) / [com.robotemi.sdk.listeners](../index.md) / [OnGoToLocationStatusChangedListener](index.md) / [onGoToLocationStatusChanged](./on-go-to-location-status-changed.md)

# onGoToLocationStatusChanged

`abstract fun onGoToLocationStatusChanged(location: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, status: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, descriptionId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Listen for status changes during 'go to location'.

Available statuses:

* [OnGoToLocationStatusChangedListener.START](-s-t-a-r-t.md)
* [OnGoToLocationStatusChangedListener.CALCULATING](-c-a-l-c-u-l-a-t-i-n-g.md)
* [OnGoToLocationStatusChangedListener.GOING](-g-o-i-n-g.md)
* [OnGoToLocationStatusChangedListener.COMPLETE](-c-o-m-p-l-e-t-e.md)
* [OnGoToLocationStatusChangedListener.ABORT](-a-b-o-r-t.md)
* [OnGoToLocationStatusChangedListener.REPOSING](-r-e-p-o-s-i-n-g.md)

### Parameters

`location` - Location of GoTo response.

`status` - Current status.