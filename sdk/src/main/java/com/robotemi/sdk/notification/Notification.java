package com.robotemi.sdk.notification;

import android.graphics.Bitmap;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public interface Notification {

    String getNotificationId();

    NormalNotification.Type getType();

    String getTitle();

    String getSubtitle();

    int getTitleResource();

    @StringRes
    int getSubtitleResource();

    @DrawableRes
    int getIconResource();

    @Nullable
    Bitmap getBitmap();

    enum Type {
        DEBUG,
        ERROR,
        INFO,
        SUCCESS
    }

}
