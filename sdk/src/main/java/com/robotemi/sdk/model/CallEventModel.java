package com.robotemi.sdk.model;


import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

public class CallEventModel implements Parcelable {

    public static final int TYPE_INCOMING = 0;

    public static final int TYPE_OUTGOING = 1;

    public static final int STATE_STARTED = 0;

    public static final int STATE_ENDED = 1;

    private String sessionId;

    private int type;

    private int state;

    public CallEventModel() {
    }

    public CallEventModel(String sessionId, int type, int state) {
        this.sessionId = sessionId;
        this.type = type;
        this.state = state;
    }

    @NotNull
    @Override
    public String toString() {
        return "CallEventModel{" +
                "sessionId='" + sessionId + '\'' +
                ", type=" + type +
                ", state=" + state +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sessionId);
        dest.writeInt(this.type);
        dest.writeInt(this.state);
    }

    protected CallEventModel(Parcel in) {
        this.sessionId = in.readString();
        this.type = in.readInt();
        this.state = in.readInt();
    }

    public static final Parcelable.Creator<CallEventModel> CREATOR = new Parcelable.Creator<CallEventModel>() {
        @Override
        public CallEventModel createFromParcel(Parcel source) {
            return new CallEventModel(source);
        }

        @Override
        public CallEventModel[] newArray(int size) {
            return new CallEventModel[size];
        }
    };
}
