package com.robotemi.sdk.listeners

import com.robotemi.sdk.permission.Permission
import com.robotemi.sdk.permission.Result

interface OnRequestPermissionResultListener {
    fun onRequestPermissionResult(
        permission: Permission,
        @Result grantResult: Int
    )
}