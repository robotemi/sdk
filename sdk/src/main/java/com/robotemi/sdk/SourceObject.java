package com.robotemi.sdk;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SourceObject implements Parcelable {

    public static final Creator<SourceObject> CREATOR = new Creator<SourceObject>() {
        @Override
        public SourceObject createFromParcel(Parcel source) {
            return new SourceObject(source);
        }

        @Override
        public SourceObject[] newArray(int size) {
            return new SourceObject[size];
        }
    };

    @Expose
    @SerializedName("name")
    String name;
    @Expose
    @SerializedName("iconUri")
    String iconUri;

    private SourceObject(String name, String iconUri) {
        this.name = name;
        this.iconUri = iconUri;
    }

    private SourceObject(Parcel in) {
        name = in.readString();
        iconUri = in.readString();
    }

    public static SourceObject create(String name, String iconUri) {
        return new SourceObject(name, iconUri);
    }

    public String getName() {
        return name;
    }

    public String getIconUri() {
        return iconUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(iconUri);
    }
}
