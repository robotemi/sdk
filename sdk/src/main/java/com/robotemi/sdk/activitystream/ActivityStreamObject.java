package com.robotemi.sdk.activitystream;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.robotemi.sdk.MediaObject;
import com.robotemi.sdk.SourceObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class ActivityStreamObject implements Parcelable {

    public static final Creator<ActivityStreamObject> CREATOR = new Creator<ActivityStreamObject>() {
        @Override
        public ActivityStreamObject createFromParcel(Parcel source) {
            return new ActivityStreamObject(source);
        }

        @Override
        public ActivityStreamObject[] newArray(int size) {
            return new ActivityStreamObject[size];
        }
    };

    @Expose
    @SerializedName("items")
    private List<ActivityStreamListItem> items;

    @Expose
    @SerializedName("type")
    private String activityType;

    @Expose
    @SerializedName("media")
    private MediaObject mediaObject;

    @Expose
    @SerializedName("source")
    private SourceObject sourceObject;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("message")
    private String message;

    private int numOfProvidedFiles;

    private String uuid;

    private ActivityStreamObject(@NonNull final String activityType,
                                 @Nullable final MediaObject mediaObject,
                                 @NonNull final SourceObject sourceObject,
                                 @NonNull final String uuid,
                                 @NonNull final String date,
                                 @NonNull final String title,
                                 @Nullable final String message,
                                 @Nullable final List<ActivityStreamListItem> items) {
        this.activityType = activityType;
        this.mediaObject = mediaObject;
        this.sourceObject = sourceObject;
        this.uuid = uuid;
        this.date = date;
        this.title = title;
        this.message = message;
        this.items = items;
        countFilesNum();
    }

    private ActivityStreamObject(Parcel in) {
        activityType = in.readString();
        mediaObject = in.readParcelable(MediaObject.class.getClassLoader());
        sourceObject = in.readParcelable(SourceObject.class.getClassLoader());
        uuid = in.readString();
        date = in.readString();
        title = in.readString();
        message = in.readString();
        items = new ArrayList<>();
        in.readTypedList(items, ActivityStreamListItem.CREATOR);
        numOfProvidedFiles = in.readInt();
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getActivityType() {
        return activityType;
    }

    public MediaObject getMediaObject() {
        return mediaObject;
    }

    public SourceObject getSourceObject() {
        return sourceObject;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public List<ActivityStreamListItem> getItems() {
        return items;
    }

    public int getNumOfProvidedFiles() {
        return numOfProvidedFiles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(activityType);
        dest.writeParcelable(mediaObject, flags);
        dest.writeParcelable(sourceObject, flags);
        dest.writeString(uuid);
        dest.writeString(date);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeTypedList(items);
        dest.writeInt(numOfProvidedFiles);
    }

    private void countFilesNum() {
        if (mediaObject != null) {
            numOfProvidedFiles++;
        }
        for (ActivityStreamListItem item : items) {
            if (item.isFileProvided()) {
                numOfProvidedFiles++;
            }
        }
    }

    public enum ActivityType {

        SIMPLE("simple"),
        PHOTO("photos"),
        WEB("webContent");

        private String typeName;

        ActivityType(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeName() {
            return typeName;
        }
    }

    public static class Builder {

        private final List<ActivityStreamListItem> items = new ArrayList<>();
        private ActivityType activityType = ActivityType.SIMPLE;
        private MediaObject mediaObject;
        private SourceObject sourceObject;
        private String title;
        private String message;

        public Builder activityType(@NonNull final ActivityType activityType) {
            this.activityType = activityType;
            return this;
        }

        public Builder media(@NonNull final MediaObject mediaObject) {
            this.mediaObject = mediaObject;
            return this;
        }

        public Builder title(@NonNull final String title) {
            this.title = title;
            return this;
        }

        public Builder message(@NonNull final String message) {
            this.message = message;
            return this;
        }

        public Builder source(@NonNull final SourceObject sourceObject) {
            this.sourceObject = sourceObject;
            return this;
        }

        public Builder addItem(@NonNull final ActivityStreamListItem item) {
            items.add(item);
            return this;
        }

        public ActivityStreamObject build() {
            return new ActivityStreamObject(activityType.getTypeName(),
                    mediaObject,
                    sourceObject,
                    UUID.randomUUID().toString(),
                    Long.toString(System.currentTimeMillis()),
                    title,
                    message,
                    items);
        }
    }
}
