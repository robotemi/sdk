package com.robotemi.sdk.activitystream;


import android.os.Parcel;
import android.os.Parcelable;

public class ActivityStreamPublishMessage implements Parcelable {

    public static final Creator<ActivityStreamPublishMessage> CREATOR = new Creator<ActivityStreamPublishMessage>() {
        @Override
        public ActivityStreamPublishMessage createFromParcel(Parcel in) {
            return new ActivityStreamPublishMessage(in);
        }

        @Override
        public ActivityStreamPublishMessage[] newArray(int size) {
            return new ActivityStreamPublishMessage[size];
        }
    };

    private String uuid;
    private boolean success;
    private int numOfUploadedFiles;

    private ActivityStreamPublishMessage(String uuid) {
        this.uuid = uuid;
        this.success = false;
        numOfUploadedFiles = 0;
    }

    private ActivityStreamPublishMessage(Parcel in) {
        uuid = in.readString();
        success = in.readByte() != 0;
        numOfUploadedFiles = in.readInt();
    }

    public static ActivityStreamPublishMessage create(String uuid) {
        return new ActivityStreamPublishMessage(uuid);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeInt(numOfUploadedFiles);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String uuid() {
        return uuid;
    }

    public boolean success() {
        return success;
    }

    public int numOfUploadedFiles() {
        return numOfUploadedFiles;
    }

    public void setNumOfUploadedFiles(int numOfUploadedFiles) {
        this.numOfUploadedFiles = numOfUploadedFiles;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ActivityStreamPublishMessage{" +
                "uuid='" + uuid + '\'' +
                ", success=" + success +
                ", numOfUploadedFiles=" + numOfUploadedFiles +
                '}';
    }
}
