package com.robotemi.sdk.permission

interface OnRequestPermissionResultListener {
    fun onRequestPermissionResult(
        permission: Permission,
        @Permission.PermissionResult grantResult: Int,
        requestCode: Int
    )
}