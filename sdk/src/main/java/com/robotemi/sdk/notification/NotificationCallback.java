package com.robotemi.sdk.notification;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

/**
 * a class representing a notification id and the button number that was pressed
 * used to return information to outside skills that requested
 * showing a notification with button events
 */
public class NotificationCallback implements Parcelable {

    public static final int INVALID_EVENT = -1;

    public static final int TIMEOUT_EVENT = 0;

    public static final int PRIMARY_BUTTON_EVENT = 1;

    public static final int SECONDARY_BUTTON_EVENT = 2;

    public static final Parcelable.Creator<NotificationCallback> CREATOR = new Parcelable.Creator<NotificationCallback>() {
        @Override
        public NotificationCallback createFromParcel(Parcel source) {
            return new NotificationCallback(source);
        }

        @Override
        public NotificationCallback[] newArray(int size) {
            return new NotificationCallback[size];
        }
    };

    @Nullable
    private String notificationId;

    private int event;

    public NotificationCallback(@Nullable String notificationId, int event) {
        this.notificationId = notificationId;
        this.event = event;
    }

    protected NotificationCallback(Parcel in) {
        this.notificationId = in.readString();
        this.event = in.readInt();
    }

    public static NotificationCallback empty() {
        return new NotificationCallback("", INVALID_EVENT);
    }

    @Nullable
    public String getNotificationId() {
        return notificationId;
    }

    public int getEvent() {
        return event;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.notificationId);
        dest.writeInt(this.event);
    }
}
