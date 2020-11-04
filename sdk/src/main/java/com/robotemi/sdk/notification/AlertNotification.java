package com.robotemi.sdk.notification;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.util.UUID;


public class AlertNotification implements Notification, Parcelable {

    public static final Creator<AlertNotification> CREATOR = new Creator<AlertNotification>() {
        @Override
        public AlertNotification createFromParcel(Parcel source) {
            return new AlertNotification(source);
        }

        @Override
        public AlertNotification[] newArray(int size) {
            return new AlertNotification[size];
        }
    };

    private String notificationId;

    private Notification.Type type;

    private String title; // Required

    @StringRes
    private int titleResource;

    private String subtitle;

    @StringRes
    private int subtitleResource;

    private String primaryBtnText;

    @StringRes
    private int primaryBtnTextResource;

    private String secondaryBtnText;

    @StringRes
    private int secondaryBtnTextResource;

    @DrawableRes
    private int iconResource;

    @StringRes
    private int altTextRes;

    private boolean autoTimeout = false;

    private boolean isDismissible = true;

    private int timeout;

    private Bitmap bitmap;

    private boolean quite;

    private String checkBoxText;

    private AlertNotification(Builder builder) {
        this.type = builder.type;
        this.title = builder.title;
        this.titleResource = builder.titleResource;
        this.subtitle = builder.subtitle;
        this.subtitleResource = builder.subtitleResource;
        this.primaryBtnText = builder.primaryBtnText;
        this.primaryBtnTextResource = builder.primaryBtnTextResource;
        this.secondaryBtnText = builder.secondaryBtnText;
        this.secondaryBtnTextResource = builder.secondaryBtnTextResource;
        this.iconResource = builder.iconResource;
        this.bitmap = builder.bitmap;
        this.autoTimeout = builder.autoTimeout;
        this.altTextRes = builder.altTextRes;
        this.isDismissible = builder.isDismissible;
        this.timeout = builder.timeout;
        this.quite = builder.quite;
        this.checkBoxText = builder.checkBoxText;
        this.notificationId = UUID.randomUUID().toString();
    }

    protected AlertNotification(Parcel in) {
        this.notificationId = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
        this.title = in.readString();
        this.titleResource = in.readInt();
        this.subtitle = in.readString();
        this.subtitleResource = in.readInt();
        this.primaryBtnText = in.readString();
        this.primaryBtnTextResource = in.readInt();
        this.secondaryBtnText = in.readString();
        this.secondaryBtnTextResource = in.readInt();
        this.altTextRes = in.readInt();
        this.iconResource = in.readInt();
        this.autoTimeout = in.readByte() != 0;
        this.isDismissible = in.readByte() != 0;
        this.timeout = in.readInt();
        this.bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        this.quite = in.readByte() != 0;
        this.checkBoxText = in.readString();
    }

    public static AlertNotification.Builder builder(String title) {
        return new AlertNotification.Builder(title);
    }

    @Override
    public String getNotificationId() {
        return notificationId;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public int getTitleResource() {
        return titleResource;
    }

    @Override
    public int getSubtitleResource() {
        return subtitleResource;
    }

    @Override
    public int getIconResource() {
        return iconResource;
    }

    @Nullable
    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getPrimaryBtnText() {
        return primaryBtnText;
    }

    public int getPrimaryBtnTextResource() {
        return primaryBtnTextResource;
    }

    public String getSecondaryBtnText() {
        return secondaryBtnText;
    }

    public int getSecondaryBtnTextResource() {
        return secondaryBtnTextResource;
    }

    public boolean isAutoTimeout() {
        return autoTimeout && timeout > 0;
    }

    public boolean isDismissible() {
        return isDismissible;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getAltTextRes() {
        return altTextRes;
    }

    public boolean getQuite() {
        return quite;
    }

    public String getCheckBoxText() {
        return checkBoxText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.notificationId);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeString(this.title);
        dest.writeInt(this.titleResource);
        dest.writeString(this.subtitle);
        dest.writeInt(this.subtitleResource);
        dest.writeString(this.primaryBtnText);
        dest.writeInt(this.primaryBtnTextResource);
        dest.writeString(this.secondaryBtnText);
        dest.writeInt(this.secondaryBtnTextResource);
        dest.writeInt(this.iconResource);
        dest.writeByte(this.autoTimeout ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isDismissible ? (byte) 1 : (byte) 0);
        dest.writeInt(this.timeout);
        dest.writeInt(this.altTextRes);
        dest.writeParcelable(this.bitmap, flags);
        dest.writeByte(this.quite ? (byte) 1 : (byte) 0);
        dest.writeString(this.checkBoxText);
    }

    public static class Builder {

        private static final int DEFAULT_TIMEOUT_DURATION = 20000;

        Notification.Type type;

        String title;

        @StringRes
        int titleResource;

        String subtitle;

        @StringRes
        int subtitleResource;

        String primaryBtnText;

        @StringRes
        int primaryBtnTextResource;

        String secondaryBtnText;

        @StringRes
        int secondaryBtnTextResource;

        @DrawableRes
        int iconResource;

        @StringRes
        int altTextRes;

        Bitmap bitmap;

        boolean autoTimeout = false;

        boolean isDismissible = true;

        int timeout = DEFAULT_TIMEOUT_DURATION;

        boolean quite = false;

        String checkBoxText;

        public Builder(String title) {
            this.title = title;
        }

        public AlertNotification.Builder type(Notification.Type type) {
            this.type = type;
            return this;
        }

        public AlertNotification.Builder titleResource(@StringRes int titleResource) {
            this.titleResource = titleResource;
            return this;
        }

        public AlertNotification.Builder subtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public AlertNotification.Builder subtitleResource(@StringRes int subtitleResource) {
            this.subtitleResource = subtitleResource;
            return this;
        }

        public AlertNotification.Builder primaryBtnText(String primaryBtnText) {
            this.primaryBtnText = primaryBtnText;
            return this;
        }

        public AlertNotification.Builder primaryBtnTextResource(@StringRes int primaryBtnTextResource) {
            this.primaryBtnTextResource = primaryBtnTextResource;
            return this;
        }

        public AlertNotification.Builder secondaryBtnText(String secondaryBtnText) {
            this.secondaryBtnText = secondaryBtnText;
            return this;
        }

        public AlertNotification.Builder secondaryBtnTextResource(@StringRes int secondaryBtnTextResource) {
            this.secondaryBtnTextResource = secondaryBtnTextResource;
            return this;
        }

        public AlertNotification.Builder iconResource(@DrawableRes int iconResource) {
            this.iconResource = iconResource;
            return this;
        }

        public AlertNotification.Builder altTextResource(@StringRes int altTextRes) {
            this.altTextRes = altTextRes;
            return this;
        }

        public AlertNotification.Builder bitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        public AlertNotification.Builder autoTimeout(boolean autoTimeout) {
            this.autoTimeout = autoTimeout;
            return this;
        }

        public AlertNotification.Builder dismissible(boolean isDismissible) {
            this.isDismissible = isDismissible;
            return this;
        }

        public AlertNotification.Builder timeout(int timeout) {
            this.timeout = timeout;
            if (timeout > 0) {
                autoTimeout = true;
            }
            return this;
        }

        public AlertNotification.Builder quite(boolean quite) {
            this.quite = quite;
            return this;
        }

        public AlertNotification.Builder checkBoxText(String checkBoxText) {
            this.checkBoxText = checkBoxText;
            return this;
        }

        public AlertNotification build() {
            return new AlertNotification(this);
        }
    }
}
