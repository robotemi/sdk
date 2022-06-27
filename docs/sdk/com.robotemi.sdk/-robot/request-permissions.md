//[sdk](../../../index.md)/[com.robotemi.sdk](../index.md)/[Robot](index.md)/[requestPermissions](request-permissions.md)

# requestPermissions

[androidJvm]\
fun [requestPermissions](request-permissions.md)(permissions: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Permission](../../com.robotemi.sdk.permission/-permission/index.md)&gt;, requestCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))

Request permissions.

If you already had the permission, Launcher will not handle this request again.

Add [OnRequestPermissionResultListener](../../com.robotemi.sdk.permission/-on-request-permission-result-listener/index.md) to listen the request result.

## Parameters

androidJvm

| | |
|---|---|
| permissions | A list holds the permissions you want to request. |
| requestCode | Identify which request. |
