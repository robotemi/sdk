package com.robotemi.sdk;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserInfo implements Parcelable {

    @NonNull
    private final String userId;

    @NonNull
    private final String name;

    @Nullable
    private final String picUrl;

    private final int role;

    public UserInfo(@NonNull final String userId,
                    @NonNull final String name,
                    @Nullable final String picUrl,
                    final int role) {
        this.userId = userId;
        this.name = name;
        this.picUrl = picUrl;
        this.role = role;
    }

    protected UserInfo(Parcel in) {
        userId = in.readString();
        name = in.readString();
        picUrl = in.readString();
        role = in.readInt();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @NonNull
    public String getUserId() {
        return userId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getPicUrl() {
        return picUrl;
    }

    public int getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", name='" + name +
                ", picUrl=" + picUrl +
                ", role=" + role +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeString(picUrl);
        dest.writeInt(role);
    }
}
