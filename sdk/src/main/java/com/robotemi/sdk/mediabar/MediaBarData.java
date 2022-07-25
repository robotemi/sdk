package com.robotemi.sdk.mediabar;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class MediaBarData implements Parcelable {

    public static final Creator<MediaBarData> CREATOR = new Creator<MediaBarData>() {
        @Override
        public MediaBarData createFromParcel(Parcel source) {
            return new MediaBarData(source);
        }

        @Override
        public MediaBarData[] newArray(int size) {
            return new MediaBarData[size];
        }
    };

    @StringRes
    private final int titleResource;

    @StringRes
    private final int subtitleResource;

    @DrawableRes
    private final int iconResource;

    private final Type type;

    private final Bitmap bitmap;

    private String title = "";

    private String subtitle = "";

    private String imageUrl = "";

    private int trackDuration;

    private int currentTrackPosition;

    private boolean isPlaying;

    private String packageName;

    private MediaBarData(Builder builder) {
        this.title = builder.title;
        this.titleResource = builder.titleResource;
        this.subtitle = builder.subtitle;
        this.imageUrl = builder.imageUrl;
        this.subtitleResource = builder.subtitleResource;
        this.iconResource = builder.iconResource;
        this.bitmap = builder.bitmap;
        this.type = builder.type;
        this.trackDuration = builder.trackDuration;
        this.currentTrackPosition = builder.currentTrackPosition;
        this.isPlaying = builder.isPlaying;
    }

    protected MediaBarData(Parcel in) {
        this.titleResource = in.readInt();
        this.subtitleResource = in.readInt();
        this.iconResource = in.readInt();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
        this.bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        this.title = in.readString();
        this.subtitle = in.readString();
        this.imageUrl = in.readString();
        this.trackDuration = in.readInt();
        this.currentTrackPosition = in.readInt();
        this.isPlaying = in.readByte() != 0;
        this.packageName = in.readString();
    }

    public static MediaBarData.Builder builder(String title, Type type) {
        return new MediaBarData.Builder(title, type);
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getTitleResource() {
        return titleResource;
    }

    public int getSubtitleResource() {
        return subtitleResource;
    }

    public int getIconResource() {
        return iconResource;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Type getType() {
        return type;
    }

    public int getTrackDuration() {
        return trackDuration;
    }

    public long getCurrentTrackPosition() {
        return currentTrackPosition;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public String toString() {
        return "MediaBarData{" + "titleResource=" + titleResource + ", subtitleResource=" + subtitleResource + ", iconResource=" + iconResource + ", bitmap=" + bitmap + ", title='" + title + '\'' + ", subtitle='" + subtitle + '\'' + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.titleResource);
        dest.writeInt(this.subtitleResource);
        dest.writeInt(this.iconResource);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeParcelable(this.bitmap, flags);
        dest.writeString(this.title);
        dest.writeString(this.subtitle);
        dest.writeString(this.imageUrl);
        dest.writeInt(this.trackDuration);
        dest.writeInt(this.currentTrackPosition);
        dest.writeByte(this.isPlaying ? (byte) 1 : (byte) 0);
        dest.writeString(this.packageName);
    }

    public enum Type {
        LIVE,
        ARTIST,
        PODCAST,
        NONE
    }

    public static class Builder {

        String title;

        Type type;

        @StringRes
        int titleResource;

        String subtitle;

        String imageUrl;

        @StringRes
        int subtitleResource;

        @DrawableRes
        int iconResource;

        Bitmap bitmap;

        private int trackDuration;

        private int currentTrackPosition;

        private boolean isPlaying;

        public Builder(String title, Type type) {
            this.title = title;
            this.type = type;
        }

        public MediaBarData.Builder titleResource(@StringRes int titleResource) {
            this.titleResource = titleResource;
            return this;
        }

        public MediaBarData.Builder subtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public MediaBarData.Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public MediaBarData.Builder subtitleResource(@StringRes int subtitleResource) {
            this.subtitleResource = subtitleResource;
            return this;
        }

        public MediaBarData.Builder iconResource(@DrawableRes int iconResource) {
            this.iconResource = iconResource;
            return this;
        }

        public MediaBarData.Builder bitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        // In seconds
        public MediaBarData.Builder trackDuration(int trackDuration) {
            this.trackDuration = trackDuration;
            return this;
        }

        /**
         * Call this method upon pause to resume from current position of track.
         * Skill should store the current track position on pause
         *
         * @param currentTrackPosition
         * @return
         */
        public MediaBarData.Builder currentTrackPosition(int currentTrackPosition) {
            this.currentTrackPosition = currentTrackPosition;
            return this;
        }

        public MediaBarData.Builder playing(boolean isPlaying) {
            this.isPlaying = isPlaying;
            return this;
        }

        public MediaBarData build() {
            return new MediaBarData(this);
        }
    }
}