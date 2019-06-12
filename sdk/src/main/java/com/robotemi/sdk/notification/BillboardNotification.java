package com.robotemi.sdk.notification;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.util.UUID;

public class BillboardNotification implements Notification {

    private String notificationId = UUID.randomUUID().toString();

    private String title;

    @StringRes
    private int titleResource;

    private String subtitle;

    @StringRes
    private int subtitleResource;

    private int timeout;

    private BillboardNotification(Builder builder) {
        this.title = builder.title;
        this.titleResource = builder.titleResource;
        this.subtitle = builder.subtitle;
        this.subtitleResource = builder.subtitleResource;
        this.timeout = builder.timeout;
    }

    @Override
    public String getNotificationId() {
        return notificationId;
    }

    @Override
    public Type getType() {
        return Type.INFO;
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
        return 0;
    }

    public int getTimeout() {
        return timeout;
    }

    @Nullable
    @Override
    public Bitmap getBitmap() {
        return null;
    }

    public static class Builder {

        private String title;

        private String subtitle;

        @StringRes
        private int titleResource;

        @StringRes
        private int subtitleResource;

        private int timeout;

        public Builder titleResource(@StringRes int titleResource) {
            this.titleResource = titleResource;
            return this;
        }

        public Builder subtitleResource(@StringRes int subtitleResource) {
            this.subtitleResource = subtitleResource;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder subtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        /**
         * Set timeout in seconds to billboard for auto dismiss
         * @param timeout 0 for disabled
         * @return Builder
         */
        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public BillboardNotification build() {
            return new BillboardNotification(this);
        }
    }
}
