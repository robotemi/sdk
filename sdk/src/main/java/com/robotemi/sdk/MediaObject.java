package com.robotemi.sdk;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

import androidx.annotation.NonNull;


public class MediaObject implements Parcelable, MediaContainer {

    public static final Creator<MediaObject> CREATOR = new Creator<MediaObject>() {
        @Override
        public MediaObject createFromParcel(Parcel source) {
            return new MediaObject(source);
        }

        @Override
        public MediaObject[] newArray(int size) {
            return new MediaObject[size];
        }
    };

    @Expose
    @SerializedName("mimetype")
    MimeType mimeType;

    @Expose
    @SerializedName("url")
    String uri;

    String localPath = "";

    private File file;

    private MediaObject(MediaObject.MimeType mimeType, File file) {
        this.mimeType = mimeType;
        this.file = file;
    }

    private MediaObject(Parcel in) {
        mimeType = (MimeType) in.readSerializable();
        uri = in.readString();
        localPath = in.readString();
    }

    public static MediaObject create(MimeType mimeType, File file) {
        return new MediaObject(mimeType, file);
    }

    @Override
    public MimeType getMimeType() {
        return mimeType;
    }

    @Override
    public String getMediaUri() {
        return uri;
    }

    @Override
    public void setMediaUri(String uri) {
        this.file = null;
        this.uri = uri;
        if (!TextUtils.isEmpty(uri) && new File(uri).exists()) {
            localPath = uri;
        }
    }

    @Override
    @NonNull
    public String getLocalPath() {
        return localPath;
    }

    public File getFile() {
        return file;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(mimeType);
        dest.writeString(uri);
        dest.writeString(localPath);
    }

    public enum MimeType {
        IMAGE,
        VIDEO,
        GIF,
        NONE
    }
}
