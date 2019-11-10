package com.robotemi.sdk.listeners

import com.robotemi.sdk.UserInfo

abstract class OnUsersUpdatedListener(var userIds: List<String>?) {

    /**
     * Called when users info was changed.
     *
     */
    abstract fun onUserUpdated(user: UserInfo)
}