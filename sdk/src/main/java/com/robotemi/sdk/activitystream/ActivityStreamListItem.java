package com.robotemi.sdk.activitystream;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.robotemi.sdk.MediaContainer;
import com.robotemi.sdk.MediaObject;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ActivityStreamListItem implements Parcelable, MediaContainer {

    public static final Creator<ActivityStreamListItem> CREATOR = new Creator<ActivityStreamListItem>() {
        @Override
        public ActivityStreamListItem createFromParcel(Parcel in) {
            return new ActivityStreamListItem(in);
        }

        @Override
        public ActivityStreamListItem[] newArray(int size) {
            return new ActivityStreamListItem[size];
        }
    };

    private static final String TAG = "ActivityStreamListItem";

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("mediaUrl")
    private String mediaUri;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("mimetype")
    private MediaObject.MimeType mimeType;

    private boolean isFileProvided = false;

    private File file;

    private String localPath = "";

    private ActivityStreamListItem(@NonNull final String title,
                                   @NonNull final String message,
                                   @NonNull final String mediaUri,
                                   @Nullable final String url,
                                   @Nullable final String date,
                                   @Nullable final MediaObject.MimeType mimeType) {
        this.title = title;
        this.message = message;
        this.mediaUri = mediaUri;
        this.url = url;
        this.date = date;
        this.mimeType = mimeType;
    }

    public ActivityStreamListItem(@NonNull final String title,
                                  @NonNull final String message,
                                  @NonNull final File file,
                                  @Nullable String url,
                                  @Nullable String date,
                                  @Nullable final MediaObject.MimeType mimeType) {
        this.title = title;
        this.message = message;
        this.file = file;
        this.url = url;
        this.date = date;
        this.mimeType = mimeType;
        this.isFileProvided = true;
    }

    private ActivityStreamListItem(Parcel in) {
        title = in.readString();
        message = in.readString();
        mediaUri = in.readString();
        url = in.readString();
        date = in.readString();
        isFileProvided = (Boolean) in.readValue(null);
        localPath = in.readString();
        mimeType = (MediaObject.MimeType) in.readSerializable();
    }

    public static Builder builder() {
        return new Builder();
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @Nullable
    public File getFile() {
        return file;
    }

    @Nullable
    @Override
    public String getMediaUri() {
        return mediaUri;
    }

    @Override
    public void setMediaUri(String uri) {
        this.file = null;
        this.mediaUri = uri;
        if (!TextUtils.isEmpty(mediaUri) && new File(mediaUri).exists()) {
            localPath = mediaUri;
        }
    }

    @Override
    public String getLocalPath() {
        return localPath;
    }

    @Override
    public MediaObject.MimeType getMimeType() {
        return mimeType;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    @Nullable
    public String getDate() {
        return date;
    }

    public boolean isFileProvided() {
        return isFileProvided;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(mediaUri);
        dest.writeString(url);
        dest.writeString(date);
        dest.writeValue(isFileProvided);
        dest.writeString(localPath);
        dest.writeSerializable(mimeType);
    }

    public static class Builder {
        private String title;
        private String message;
        private String mediaUrl;
        private File mediaFile;
        private String uri;
        private String date;
        private MediaObject.MimeType mimeType = MediaObject.MimeType.NONE;

        public Builder title(@NonNull final String title) {
            this.title = title;
            return this;
        }

        public Builder message(@NonNull final String message) {
            this.message = message;
            return this;
        }

        public Builder mediaUrl(@NonNull final String mediaUrl) {
            this.mediaUrl = mediaUrl;
            return this;
        }

        public Builder mimeType(@NonNull final MediaObject.MimeType mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public Builder date(@NonNull final String date) {
            this.date = date;
            return this;
        }

        public Builder mediaFile(@NonNull final File mediaFile) {
            this.mediaFile = mediaFile;
            return this;
        }

        public Builder url(@NonNull final String uri) {
            this.uri = uri;
            return this;
        }

        public ActivityStreamListItem build() {
            if (mediaFile != null) {
                if (mediaUrl != null) {
                    Log.e(TAG, "Provide either media File or media url. No need to provide both");
                }
                return new ActivityStreamListItem(title, message, mediaFile, uri, date, mimeType);
            } else {
                return new ActivityStreamListItem(title, message, mediaUrl, uri, date, mimeType);
            }
        }
    }
}
