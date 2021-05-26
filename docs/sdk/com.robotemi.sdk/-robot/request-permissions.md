[sdk](../../index.md) / [com.robotemi.sdk](../index.md) / [Robot](index.md) / [requestPermissions](./request-permissions.md)

# requestPermissions

`fun requestPermissions(permissions: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Permission`](../../com.robotemi.sdk.permission/-permission/index.md)`>, requestCode: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Request permissions.

If you already had the permission, Launcher will not handle this request again.

Add [OnRequestPermissionResultListener](../../com.robotemi.sdk.permission/-on-request-permission-result-listener/index.md) to listen the request result.

### Parameters

`permissions` - A list holds the permissions you want to request.

`requestCode` - Identify which request.