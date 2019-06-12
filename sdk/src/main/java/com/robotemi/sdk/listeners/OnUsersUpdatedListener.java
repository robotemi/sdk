package com.robotemi.sdk.listeners;

import com.robotemi.sdk.UserInfo;

import java.util.List;

import androidx.annotation.Nullable;

public abstract class OnUsersUpdatedListener {

    @Nullable
    public List<String> userIds;

    public OnUsersUpdatedListener(@Nullable List<String> userIds) {
        this.userIds = userIds;
    }

    /**
     * Called when users info was changed.
     *
     */
    public abstract void onUserUpdated(UserInfo user);
}
