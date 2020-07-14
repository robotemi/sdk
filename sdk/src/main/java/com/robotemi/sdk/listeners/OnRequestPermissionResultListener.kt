package com.robotemi.sdk.listeners

import com.robotemi.sdk.permission.Permission

interface OnRequestPermissionResultListener {
    fun onRequestPermissionResult(
        permission: Permission,
        @Permission.PermissionResult grantResult: Int,
        requestCode: Int
    )
}