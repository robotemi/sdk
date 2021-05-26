[sdk](../../index.md) / [com.robotemi.sdk.listeners](../index.md) / [OnBeWithMeStatusChangedListener](index.md) / [onBeWithMeStatusChanged](./on-be-with-me-status-changed.md)

# onBeWithMeStatusChanged

`abstract fun onBeWithMeStatusChanged(status: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Listen for status changes during 'beWithMe'.

Available statuses:

* [OnBeWithMeStatusChangedListener.ABORT](-a-b-o-r-t.md)
* [OnBeWithMeStatusChangedListener.CALCULATING](-c-a-l-c-u-l-a-t-i-n-g.md)
* [OnBeWithMeStatusChangedListener.SEARCH](-s-e-a-r-c-h.md)
* [OnBeWithMeStatusChangedListener.START](-s-t-a-r-t.md)
* [OnBeWithMeStatusChangedListener.TRACK](-t-r-a-c-k.md)
* [OnBeWithMeStatusChangedListener.OBSTACLE_DETECTED](-o-b-s-t-a-c-l-e_-d-e-t-e-c-t-e-d.md)

### Parameters

`status` - Current status.